package repositories;

import models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepo extends JpaRepository<Question,Long> {
}
