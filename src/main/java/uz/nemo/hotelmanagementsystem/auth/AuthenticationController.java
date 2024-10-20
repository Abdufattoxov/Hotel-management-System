package uz.nemo.hotelmanagementsystem.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import uz.nemo.hotelmanagementsystem.dto.requests.UserRequestDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.service.JwtBlackListService;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Handles authentication and registration of users")
public class AuthenticationController {

    private final AuthenticationService service;
    private final JwtBlackListService jwtBlackListService;

    @Operation(summary = "User Login", description = "Authenticate a user with their credentials")
    @PostMapping({"/login"})
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticate = service.login(request);
        return ResponseEntity.ok(authenticate);
    }

    @Operation(summary = "Register User", description = "Register a new user with provided details")
    @PostMapping({"/register"})
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        ApiResponse<String> stringApiResponse = service.registerUser(userRequestDto);
        return ResponseEntity.ok(stringApiResponse);
    }

    @Operation(summary = "Verify Email", description = "Verify user email using a token")
    @GetMapping({"/verify-email"})
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam("token") String token) {
        ApiResponse<String> stringApiResponse = service.verifyUser(token);
        return ResponseEntity.ok(stringApiResponse);
    }

    @Operation(summary = "Forgot Password", description = "Send reset password instructions to the user's email")
    @PostMapping({"/forgot-password"})
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestParam("email") String email) {
        ApiResponse<String> stringApiResponse = service.sendForgotPasswordEmail(email);
        return ResponseEntity.ok(stringApiResponse);
    }

    @Operation(summary = "Reset Password", description = "Reset user password with the provided token and new password")
    @PostMapping({"/reset-password"})
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestParam("token") String token, @RequestBody String newPassword) {
        ApiResponse<String> stringApiResponse = service.resetPassword(token, newPassword);
        return ResponseEntity.ok(stringApiResponse);
    }

    @Operation(summary = "Reset Password Form", description = "Display an HTML form for resetting the password")
    @GetMapping({"/reset-password-form"})
    public ResponseEntity<String> showResetPasswordForm(@RequestParam("token") String token) {
        String htmlForm = "<html>\n" +
                "  <body>\n" +
                "    <h1>Reset Password</h1>\n" +
                "    <form action=\"/api/v1/auth/reset-password\" method=\"POST\">\n" +
                "      <input type=\"hidden\" name=\"token\" value=\"` + token + `\">\n" +
                "      <label for=\"newPassword\">New Password:</label>\n" +
                "      <input type=\"password\" name=\"newPassword\" required>\n" +
                "      <button type=\"submit\">Reset Password</button>\n" +
                "    </form>\n" +
                "  </body>\n" +
                "</html>\n";
        return ResponseEntity.ok().body(htmlForm);
    }

    @Operation(summary = "Logout", description = "Log out the user and invalidate the JWT token")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("User is not logged in", HttpStatus.UNAUTHORIZED);
        }
        String extractedJwt = extractJwt(request);

        if (extractedJwt == null) {
            return new ResponseEntity<>("Token not provided", HttpStatus.BAD_REQUEST);
        }

        if (jwtBlackListService.blackListToken(extractedJwt)) {
            request.getSession().invalidate();
            return new ResponseEntity<>("Logout successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid token or bad request", HttpStatus.UNAUTHORIZED);
    }

    private String extractJwt(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        if (StringUtils.hasText(jwt) && jwt.startsWith("Bearer ")) {
            return jwt.substring(7);
        }
        return null;
    }
}
