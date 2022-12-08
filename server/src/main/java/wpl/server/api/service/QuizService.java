package wpl.server.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wpl.server.api.exception.NotFoundException;
import wpl.server.api.repository.QuizRepository;
import wpl.server.entity.Quiz;
import wpl.server.payload.Message;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final QuizRepository quizRepository;

    public Message allQuizzes() {
        return new Message("success to search all quizzes",
                quizRepository.findAll()
                        .stream()
                        .map(Quiz::convertToDto)
                        .collect(Collectors.toList()));
    }

    public Message quizInfo(Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isEmpty()) {
            throw new NotFoundException("cannot find quiz with id: " + id);
        }
        return new Message("success to find quiz with id: " + id, Quiz.convertToDto(quizOptional.get()));
    }

}
