package wpl.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wpl.server.api.service.QuizService;
import wpl.server.payload.Message;

@RestController
@RequestMapping("/api/v0/quiz")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizController {

    private final QuizService quizService;

    @GetMapping("")
    public ResponseEntity<Message> allQuizzes() {
        return ResponseEntity.ok(quizService.allQuizzes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> quizInfo(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.quizInfo(id));
    }
}
