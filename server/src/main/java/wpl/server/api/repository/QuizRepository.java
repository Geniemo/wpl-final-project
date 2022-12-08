package wpl.server.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wpl.server.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
