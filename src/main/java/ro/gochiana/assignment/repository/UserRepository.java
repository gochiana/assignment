package ro.gochiana.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.gochiana.assignment.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
}
