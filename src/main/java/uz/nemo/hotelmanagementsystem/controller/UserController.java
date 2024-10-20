package uz.nemo.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nemo.hotelmanagementsystem.dto.requests.UserRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.UserResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;
import uz.nemo.hotelmanagementsystem.service.interfaces.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Allows an admin to create a new user.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody UserRequestDto userRequestDto) {
        ApiResponse<Void> response = userService.create(userRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get user by ID", description = "Allows an admin or customer to retrieve a user by their ID.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> get(@PathVariable Long id) {
        ApiResponse<UserResponseDto> response = userService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all users", description = "Allows an admin to retrieve a paginated list of users.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PaginationResponse> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size,
                                                     @RequestParam(defaultValue = "firstName") String sortedBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        PaginationResponse response = userService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update user information", description = "Allows a user or admin to update their or another user's information.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@Valid @RequestBody UserRequestDto userRequestDto,
                                                    @PathVariable Long id) {
        ApiResponse<Void> response = userService.update(userRequestDto, id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a user", description = "Allows an admin or customer to delete a user.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        ApiResponse<Void> response = userService.delete(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Make a user an admin", description = "Allows an admin to promote a user to admin.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{adminUserId}")
    public ResponseEntity<ApiResponse<Void>> makeUserAdmin(@PathVariable Long adminUserId,
                                                           @RequestParam Long userId) {
        ApiResponse<Void> response = userService.makeUserAdmin(adminUserId, userId);
        return ResponseEntity.ok(response);
    }
}
