package uz.nemo.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nemo.hotelmanagementsystem.dto.requests.RoomRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.RoomResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;
import uz.nemo.hotelmanagementsystem.service.interfaces.RoomService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private final RoomService roomService;

    @Operation(summary = "Create a new room", description = "Allows an admin to create a new room.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody RoomRequestDto roomRequestDto) {
        ApiResponse<Void> room = roomService.create(roomRequestDto);
        return ResponseEntity.ok(room);
    }

    @Operation(summary = "Get room by ID", description = "Allows an admin or customer to get details of a room by its ID.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomResponseDto>> get(@PathVariable Long id) {
        ApiResponse<RoomResponseDto> response = roomService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all rooms", description = "Allows an admin or customer to retrieve a paginated list of rooms.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping
    public ResponseEntity<PaginationResponse> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size,
                                                     @RequestParam(defaultValue = "roomNumber") String sortedBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        PaginationResponse response = roomService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update room", description = "Allows an admin to update an existing room.")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id, @Valid @RequestBody RoomRequestDto roomRequestDto) {
        ApiResponse<Void> response = roomService.update(id, roomRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete room", description = "Allows an admin to delete a room.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        ApiResponse<Void> response = roomService.delete(id);
        return ResponseEntity.ok(response);
    }
}
