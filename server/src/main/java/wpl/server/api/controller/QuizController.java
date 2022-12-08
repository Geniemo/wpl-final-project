package wpl.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import wpl.server.api.service.QuizService;
import wpl.server.payload.Message;
import wpl.server.payload.request.UploadQuizRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v0/quiz")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizController {

    private final QuizService quizService;

    @PostMapping("")
    @Transactional
    public ResponseEntity<Message> upload(@Valid UploadQuizRequest uploadQuizRequest) {
        return ResponseEntity.ok(quizService.upload(uploadQuizRequest));
    }

    @GetMapping("")
    public ResponseEntity<Message> allQuizzes() {
        return ResponseEntity.ok(quizService.allQuizzes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> quizInfo(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.quizInfo(id));
    }
}
