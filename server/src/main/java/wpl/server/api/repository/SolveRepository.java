package wpl.server.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wpl.server.entity.Solve;

public interface SolveRepository extends JpaRepository<Solve, Long> {
}
