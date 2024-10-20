package uz.nemo.hotelmanagementsystem.dto.requests;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record UserRequestDto(
        @NotNull(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        String lastName,
        @Email(message = "Email should be valid")
        @NotNull(message = "Email is required")
        String email,
        @NotNull(message = "Phone number is required")
        String phoneNumber,
        @NotNull(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 character")
        String password,
        LocalDate birthDate
) {}
