package uz.nemo.hotelmanagementsystem.dto.responses;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {}
