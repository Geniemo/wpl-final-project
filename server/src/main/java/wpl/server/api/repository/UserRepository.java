package wpl.server.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wpl.server.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailOrName(String email, String name);
}
