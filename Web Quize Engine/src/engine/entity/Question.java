package engine.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Questions")
@JsonPropertyOrder({ "id", "title", "text", "options" })
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    @NotEmpty
    @NotNull
    private String title;
    @Column
    @NotEmpty
    @NotNull
    @NotBlank
    private String text;
    @Column
    @Size(min = 2)
    @NotNull
    private String[] options;
    @Column
    private int[] answer;
    @Column
    private String author;
    @Column
    private LocalDateTime[] completedAt;
    @Column
    private String[] completedBy;

    public Question() {

    }

    public Question(int id, String title, String text, String[] options, int[] answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    @JsonCreator
    public Question(@JsonProperty("title") String title,
                    @JsonProperty("text") String text,
                    @JsonProperty("options") String[] options,
                    @JsonProperty("answer") int[] answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    @JsonGetter("id")
    public int getId() {
        return id;
    }

    @JsonGetter("title")
    public String getTitle() {
        return title;
    }

    @JsonGetter("text")
    public String getText() {
        return text;
    }

    @JsonGetter("options")
    public String[] getOptions() {
        return options;
    }

    @JsonIgnore
    public int[] getAnswer() {
        return answer;
    }

    @JsonIgnore
    public String getAuthor() { return author; }

    @JsonIgnore
    public LocalDateTime[] getCompletedAt() {
        return completedAt;
    }

    @JsonIgnore
    public String[] getCompletedBy() {
        return completedBy;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
