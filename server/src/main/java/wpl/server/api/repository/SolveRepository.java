package wpl.server.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wpl.server.entity.Quiz;
import wpl.server.entity.Solve;
import wpl.server.entity.User;

import java.util.Optional;

public interface SolveRepository extends JpaRepository<Solve, Long> {
    Optional<Solve> findByUserAndQuiz(User user, Quiz quiz);
}
