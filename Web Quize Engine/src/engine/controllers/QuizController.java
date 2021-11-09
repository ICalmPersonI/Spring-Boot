package engine.controllers;

import engine.comparators.SortByCompletedAt;
import engine.comparators.SortById;
import engine.entity.*;
import engine.entity.JSONEntitiy.JSONContentCompleted;
import engine.entity.JSONEntitiy.JSONContentQuestion;
import engine.repositories.QuestionRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RequestMapping("/api/quizzes")
@RestController
public class QuizController {

    @Autowired
    QuestionRepository repository;

    @GetMapping
    private ResponseEntity<String> getAllQuestions(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        List<Question> records = repository.findAll();
        int skip = pageSize * page;

        List<JSONContentQuestion> content = records.stream()
                .map(elem ->
                        new JSONContentQuestion(
                                elem.getId(), elem.getTitle(),
                                elem.getText(), elem.getOptions()
                        )
                ).sorted(new SortById()).collect(Collectors.toList());


        JSONObject object = new JSONObject();
        object.put("content", content.stream().skip(skip).limit(pageSize).collect(Collectors.toList()));

        return new ResponseEntity(object.toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("completed")
    private ResponseEntity<String> getCompletedQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<JSONContentCompleted> content = new ArrayList<>();
        int skip = pageSize * page;

        List<Question> records = repository.findAll();
        records.stream()
                .filter(elem -> {
                    String[] completedBy = repository.findById(elem.getId()).get().getCompletedBy();
                    return Arrays.stream(completedBy).anyMatch(e -> e.equals(auth.getName()));
                })
                .forEach(elem -> {
                            String[] completedBy = repository.findById(elem.getId()).get().getCompletedBy();
                            LocalDateTime[] completedAt = repository.findById(elem.getId()).get().getCompletedAt();
                            for (int i = 0; i < completedBy.length; i++) {
                                if (completedBy[i].equals(auth.getName())) {
                                    content.add(new JSONContentCompleted(elem.getId(), completedAt[i].toString()));
                                }
                            }
                        }
                );

        Collections.sort(content, new SortByCompletedAt());
        Collections.reverse(content);

        JSONObject object = new JSONObject();
        object.put("content", content.stream().skip(skip).limit(pageSize).collect(Collectors.toList()));

        return new ResponseEntity(object.toString(), new HttpHeaders(), HttpStatus.OK);
    }


    @PostMapping()
    private Question addAnswer(@Valid @RequestBody Question question) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        question.setAuthor(auth.getName());
        return repository.save(question);
    }

    @PostMapping("{id}/solve")
    private Result solve(HttpServletResponse response, @PathVariable int id, @RequestBody Answer answer) {
        Optional<Question> dbQuestion = repository.findById(id);
        if (dbQuestion.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } else {
            int[] rightAnswer = dbQuestion.get().getAnswer();
            int[] receivedAnswer = answer.getAnswer();

            if (Arrays.equals(rightAnswer, receivedAnswer) ||
                    (rightAnswer == null && receivedAnswer.length == 0)) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                int dbId = dbQuestion.get().getId();

                List<LocalDateTime> completedAt;
                List<String> completedBy;

                if (dbQuestion.get().getCompletedAt() != null) {
                    completedAt = Arrays.stream(dbQuestion.get().getCompletedAt())
                            .collect(Collectors.toList());
                    completedBy = Arrays.stream(dbQuestion.get().getCompletedBy())
                            .collect(Collectors.toList());

                    completedAt.add(LocalDateTime.now());
                    completedBy.add(auth.getName());
                } else {
                    completedAt = List.of(LocalDateTime.now());
                    completedBy = List.of(auth.getName());
                }
                repository.updateCompletedAt(
                        completedAt.toArray(LocalDateTime[]::new),
                        completedBy.toArray(String[]::new),
                        dbId);

                return new Result(true, "Congratulations, you're right!");
            } else {
                return new Result(false, "Wrong answer! Please, try again.");
            }
        }
    }

    @GetMapping("{id}")
    private Question getQuestion(HttpServletResponse response, @PathVariable int id) {
        Optional<Question> dbRecord = repository.findById(id);
        if (dbRecord.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        } else {
            return dbRecord.get();
        }
    }

    @DeleteMapping("{id}")
    private void deleteQuestion(HttpServletResponse response, @PathVariable int id) {
        Optional<Question> dbRecord = repository.findById(id);

        if (dbRecord.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (dbRecord.get().getAuthor().matches(auth.getName())) {
                repository.delete(dbRecord.get());
                System.out.println("Deleted!");
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }


}
