package uz.nemo.hotelmanagementsystem.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.nemo.hotelmanagementsystem.entity.LoginAttempt;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    Optional<LoginAttempt> findByEmail(String username);

    void deleteByEmail(String username);
}
