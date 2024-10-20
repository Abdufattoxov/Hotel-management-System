package uz.nemo.hotelmanagementsystem.mapper;

import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.nemo.hotelmanagementsystem.dto.requests.UserRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.UserResponseDto;
import uz.nemo.hotelmanagementsystem.entity.User;
import uz.nemo.hotelmanagementsystem.entity.enums.Role;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder encoder;

    public User mapToEntity(UserRequestDto userRequestDto) {
        return User.builder()
                .firstName(userRequestDto.firstName())
                .lastName(userRequestDto.lastName())
                .email(userRequestDto.email())
                .phoneNumber(userRequestDto.phoneNumber())
                .password(this.encoder.encode(userRequestDto.password()))
                .dateOfBirth(userRequestDto.birthDate())
                .role(Role.CUSTOMER)
                .build();
    }

    public UserResponseDto mapToResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber());
    }

}
