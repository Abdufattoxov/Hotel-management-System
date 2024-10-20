package uz.nemo.hotelmanagementsystem.service.interfaces;

import org.springframework.data.domain.Pageable;
import uz.nemo.hotelmanagementsystem.dto.requests.UserRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.UserResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;

public interface UserService {
    ApiResponse<Void> create(UserRequestDto user);

    ApiResponse<Void> update(UserRequestDto user, Long userId);

    ApiResponse<UserResponseDto> getById(Long userId);

    PaginationResponse getAll(Pageable pageable);

    ApiResponse<Void> delete(Long userId);

    ApiResponse<Void> makeUserAdmin(Long adminUserId, Long userId);
}
