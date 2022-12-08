package wpl.server.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wpl.server.api.exception.NotFoundException;
import wpl.server.api.repository.QuizImageRepository;
import wpl.server.api.repository.QuizRepository;
import wpl.server.api.repository.SolveRepository;
import wpl.server.api.repository.UserRepository;
import wpl.server.entity.*;
import wpl.server.file_storage.File;
import wpl.server.file_storage.FileService;
import wpl.server.payload.Message;
import wpl.server.payload.request.SolveRequest;
import wpl.server.payload.request.UploadQuizRequest;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final QuizRepository quizRepository;
    private final FileService fileService;
    private final QuizImageRepository quizImageRepository;
    private final UserRepository userRepository;
    private final SolveRepository solveRepository;

    @Transactional
    public Message upload(UploadQuizRequest uploadQuizRequest) {
        Quiz quiz = Quiz.createQuiz(uploadQuizRequest.getTitle(), uploadQuizRequest.getDescription(), uploadQuizRequest.getAnswer());
        quizRepository.save(quiz);
        String path = "quiz/" + quiz.getId();
        for (MultipartFile image : uploadQuizRequest.getImages()) {
            quiz.getImages().add(quizImageRepository.save(QuizImage.createQuizImage(quiz, fileService.upload(image, path))));
        }
        return new Message("success to upload quiz", Quiz.convertToDto(quiz));
    }

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

    @Transactional
    public Message solve(SolveRequest solveRequest) {
        Optional<User> userOptional = userRepository.findById(solveRequest.getUserId());
        if (userOptional.isEmpty()) {
            throw new NotFoundException("cannot find user with id: " + solveRequest.getUserId());
        }
        Optional<Quiz> quizOptional = quizRepository.findById(solveRequest.getQuizId());
        if (quizOptional.isEmpty()) {
            throw new NotFoundException("cannot find quiz with id: " + solveRequest.getQuizId());
        }

        Optional<Solve> solveOptional = solveRepository.findByUserAndQuiz(userOptional.get(), quizOptional.get());
        if (solveOptional.isEmpty()) {
            Solve solve = Solve.createSolve(userOptional.get(), quizOptional.get());
            solve.setStatus(SolveStatus.of(solveRequest.getAnswer() == quizOptional.get().getAnswer() ? "SUCCESS" : "FAIL"));
            solveRepository.save(solve);
            return new Message(solve.getStatus().equals(SolveStatus.SUCCESS) ? "correct answer" : "wrong answer", Solve.convertToDto(solve));
        } else {
            solveOptional.get().setStatus(SolveStatus.of(solveRequest.getAnswer() == quizOptional.get().getAnswer() ? "SUCCESS" : "FAIL"));
            return new Message(solveOptional.get().getStatus().equals(SolveStatus.SUCCESS) ? "correct answer" : "wrong answer", Solve.convertToDto(solveOptional.get()));
        }
    }
}
