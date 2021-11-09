package engine.repositories;

import engine.entity.Question;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Optional<Question> findById(int id);

    List<Question> findAll();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Questions SET completedAt = :completedAt, completedBy = :completedBy" +
                    " WHERE id = :id", nativeQuery = true)
    void updateCompletedAt(
            @Param("completedAt") LocalDateTime[] completedAt,
            @Param("completedBy") String[] completedBy,
            @Param("id") int id);
}
