package repositories;

import models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);
}
