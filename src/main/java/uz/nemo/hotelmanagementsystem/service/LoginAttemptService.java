package uz.nemo.hotelmanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nemo.hotelmanagementsystem.entity.LoginAttempt;
import uz.nemo.hotelmanagementsystem.repository.LoginAttemptRepository;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private final LoginAttemptRepository loginAttemptRepository;
    private final int ATTEMPT_LIMIT = 3;

    public void loginFailed(String email) {
        LoginAttempt loginAttempt = loginAttemptRepository.findByEmail(email)
                .orElse(new LoginAttempt(email, 0, null));

        loginAttempt.setAttempts(loginAttempt.getAttempts() + 1);

        if (loginAttempt.getAttempts() >= ATTEMPT_LIMIT) {
            loginAttempt.setLockTime(LocalDateTime.now().plusMinutes(5));
        }

        loginAttemptRepository.save(loginAttempt);
    }

    public void loginSucceeded(String email) {
        loginAttemptRepository.deleteByEmail(email);
    }

    public boolean isBlocked(String email) {
        LoginAttempt loginAttempt = loginAttemptRepository.findByEmail(email).orElse(null);

        if (loginAttempt != null && loginAttempt.getAttempts() >= ATTEMPT_LIMIT) {
            if (loginAttempt.getLockTime().isAfter(LocalDateTime.now())) {
                return true;
            } else {
                loginAttemptRepository.deleteByEmail(email);
            }
        }
        return false;
    }
}
