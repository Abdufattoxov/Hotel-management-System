package uz.nemo.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.nemo.hotelmanagementsystem.dto.requests.UserRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.UserResponseDto;
import uz.nemo.hotelmanagementsystem.entity.User;
import uz.nemo.hotelmanagementsystem.entity.enums.Role;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.mapper.UserMapper;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;
import uz.nemo.hotelmanagementsystem.repository.UserRepository;
import uz.nemo.hotelmanagementsystem.service.interfaces.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ApiResponse<Void> create(UserRequestDto userRequestDto) {
        User user = userMapper.mapToEntity(userRequestDto);
        userRepository.save(user);
        return new ApiResponse<>().success();
    }

    public ApiResponse<Void> update(UserRequestDto userRequestDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException("User not found with id: " + userId);
        }
        User user = userMapper.mapToEntity(userRequestDto);
        user.setId(userId);
        userRepository.save(user);
        return new ApiResponse<>().success();

    }

    public ApiResponse<UserResponseDto> getById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException("User not found with id: " + userId);
        }
        UserResponseDto responseDto = userMapper.mapToResponse(userOptional.get());
        return new ApiResponse<UserResponseDto>().success(responseDto);
    }

    public PaginationResponse getAll(Pageable pageable) {
        Page<UserResponseDto> userResponse = userRepository.findAllUserResponse(pageable);
        return new PaginationResponse(userResponse.getTotalPages(), userResponse.getContent());
    }

    public ApiResponse<Void> delete(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
        return new ApiResponse<>().success();

    }

    public ApiResponse<Void> makeUserAdmin(Long adminUserId, Long userId) {
        Optional<User> adminOptional = userRepository.findById(adminUserId);
        if (adminOptional.isEmpty()) {
            throw new CustomNotFoundException("Admin not found with id: " + adminUserId);
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException("User not found with id: " + userId);
        }
        User user = adminOptional.get();
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return new ApiResponse<>().success();

    }

}

