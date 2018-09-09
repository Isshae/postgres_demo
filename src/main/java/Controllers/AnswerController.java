package Controllers;

import exception.ResourceNotFoundException;
import models.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.AnswerRepo;
import repositories.QuestionRepo;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AnswerController {

    AnswerRepo answerRepo;
    QuestionRepo questionRepo;

    @Autowired
    public AnswerController(AnswerRepo answerRepo, QuestionRepo questionRepo) {
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
    }

    @GetMapping("/questions/{questionId}/answers")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerRepo.findByQuestionId(questionId);
    }

    @PostMapping("/questions/{questionId}/answers")
    public Answer addAnswer(@PathVariable Long questionId,
                            @Valid @RequestBody Answer answer) {
        return questionRepo.findById(questionId)
                .map(question -> {
                    answer.setQuestion(question);
                    return answerRepo.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public Answer updateAnswer(@PathVariable Long questionId,
                               @PathVariable Long answerId,
                               @Valid @RequestBody Answer answerRequest) {
        if (!questionRepo.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }
        return answerRepo.findById(answerId)
                .map(answer -> {
                    answer.setText(answerRequest.getText());
                    return answerRepo.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId,
                                          @PathVariable Long answerId) {
        if (!questionRepo.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }
        return answerRepo.findById(answerId)
                .map(answer -> {
                    answerRepo.delete(answer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));
    }

}
