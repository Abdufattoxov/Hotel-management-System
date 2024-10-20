package uz.nemo.hotelmanagementsystem.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nemo.hotelmanagementsystem.dto.requests.UserRequestDto;
import uz.nemo.hotelmanagementsystem.entity.User;
import uz.nemo.hotelmanagementsystem.exceptions.CustomBadRequestException;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.mapper.UserMapper;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.service.JwtService;
import uz.nemo.hotelmanagementsystem.service.LoginAttemptService;
import uz.nemo.hotelmanagementsystem.utlity.EmailSender;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final uz.nemo.hotelmanagementsystem.repository.UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LoginAttemptService loginAttemptService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    private final UserMapper userMapper;

    public AuthenticationResponse login(AuthenticationRequest request) {
        if (this.loginAttemptService.isBlocked(request.getEmail())) {
            throw new LockedException("User is locked for a while due to many failed attempts, please try again later");
        } else {
            Optional<User> userOptional = this.userRepository.findByEmail(request.getEmail());
            if (userOptional.isEmpty()) {
                throw new CustomNotFoundException("User not found");
            } else if (!((User)userOptional.get()).getIsVerified()) {
                throw new CustomBadRequestException("User is not verified");
            } else {
                try {
                    this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                    this.loginAttemptService.loginSucceeded(request.getEmail());
                    User user = (User)userOptional.get();
                    String jwtToken = this.jwtService.generateToken(user);
                    return AuthenticationResponse.builder().token(jwtToken).build();
                } catch (AuthenticationException var5) {
                    this.loginAttemptService.loginFailed(request.getEmail());
                    throw new BadCredentialsException("Wrong email or password");
                }
            }
        }
    }

    public ApiResponse<String> registerUser(UserRequestDto request) {
        User user = this.userMapper.mapToEntity(request);
        user.setVerificationToken(UUID.randomUUID().toString());
        this.userRepository.save(user);
        String verificationUrl = "http://localhost:8080/api/v1/auth/verify-email?token=" + user.getVerificationToken();
        String emailContent = "<html><body><h1>Verify Your Email</h1><p>Please click the button below to verify your email:</p><a href='" + verificationUrl + "'><button>Verify</button></a></body></html>";
        this.emailSender.sendEmail(user.getEmail(), "Verify Your Email", emailContent);
        String message = "Registration successful! Please check your email to verify your account.";
        return (new ApiResponse()).success(message);
    }

    public ApiResponse<String> verifyUser(String token) {
        User user = (User)this.userRepository.findByVerificationToken(token).orElseThrow(() -> {
            return new CustomNotFoundException("Invalid token");
        });
        user.setIsVerified(true);
        user.setVerificationToken((String)null);
        this.userRepository.save(user);
        return (new ApiResponse()).success("Email verified successfully!");
    }

    public ApiResponse<String> sendForgotPasswordEmail(String email) {
        User user = (User)this.userRepository.findByEmail(email).orElseThrow(() -> {
            return new IllegalArgumentException("Email not found");
        });
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        this.userRepository.save(user);
        String resetUrl = "http://localhost:8080/api/v1/auth/reset-password-form?token=" + resetToken;
        String emailBody = "<html><body><p>Click the button below to reset your password:</p><a href=\"" + resetUrl + "\"><button>Reset Password</button></a></body></html>";
        this.emailSender.sendEmail(user.getEmail(), "Reset Your Password", emailBody);
        return (new ApiResponse()).success("Password reset email sent.");
    }

    public ApiResponse<String> resetPassword(String token, String newPassword) {
        User user = (User)this.userRepository.findByResetToken(token).orElseThrow(() -> {
            return new IllegalArgumentException("Invalid token");
        });
        user.setPassword(this.passwordEncoder.encode(newPassword));
        user.setResetToken((String)null);
        this.userRepository.save(user);
        return (new ApiResponse()).success("Password reset successfully!");
    }
}
