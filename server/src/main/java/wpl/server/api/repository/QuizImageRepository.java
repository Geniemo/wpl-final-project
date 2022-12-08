package wpl.server.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wpl.server.entity.QuizImage;

public interface QuizImageRepository extends JpaRepository<QuizImage, Long> {
}
