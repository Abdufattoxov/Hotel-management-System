package uz.nemo.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nemo.hotelmanagementsystem.dto.requests.ReservationRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.ReservationResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.service.interfaces.ReservationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Operation(summary = "Create a new reservation", description = "Allows a customer to create a new reservation.")
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        ApiResponse<Void> response = reservationService.create(reservationRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get reservation by ID", description = "Allows an admin or customer to get details of a reservation by its ID.")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationResponseDto>> getById(@PathVariable Long id) {
        ApiResponse<ReservationResponseDto> response = reservationService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get reservations by user ID", description = "Allows an admin or customer to retrieve all reservations for a specific user.")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @GetMapping("/by-user")
    public ResponseEntity<ApiResponse<List<ReservationResponseDto>>> getAllByUserId(@RequestParam Long userId) {
        ApiResponse<List<ReservationResponseDto>> response = reservationService.getAllByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get reservations by room ID", description = "Allows an admin to retrieve all reservations for a specific room.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-room")
    public ResponseEntity<ApiResponse<List<ReservationResponseDto>>> getAllByRoomId(@RequestParam Long roomId) {
        ApiResponse<List<ReservationResponseDto>> response = reservationService.getAllByRoomId(roomId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update reservation", description = "Allows a customer to update an existing reservation.")
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id, @Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        ApiResponse<Void> response = reservationService.update(id, reservationRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete reservation", description = "Allows an admin to delete a reservation.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        ApiResponse<Void> response = reservationService.delete(id);
        return ResponseEntity.ok(response);
    }
}
