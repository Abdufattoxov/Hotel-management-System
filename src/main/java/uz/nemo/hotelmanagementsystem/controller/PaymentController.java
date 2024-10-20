package uz.nemo.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nemo.hotelmanagementsystem.dto.requests.PaymentRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.PaymentResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.service.interfaces.PaymentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Process a Payment", description = "Allows a customer to process a payment")
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        ApiResponse<Void> response = paymentService.process(paymentRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Payment by ID", description = "Fetch a payment by its ID. Accessible to both admins and customers.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponseDto>> getPayment(@PathVariable Long id) {
        ApiResponse<PaymentResponseDto> response = paymentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Payments by User ID", description = "Allows an admin to retrieve all payments made by a specific user.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-user")
    public ResponseEntity<ApiResponse<List<PaymentResponseDto>>> getPaymentsByUser(@RequestParam Long userId) {
        ApiResponse<List<PaymentResponseDto>> allByUserId = paymentService.getAllByUserId(userId);
        return ResponseEntity.ok(allByUserId);
    }

    @Operation(summary = "Get Payments by Reservation ID", description = "Allows an admin to retrieve all payments for a specific reservation.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-reservation")
    public ResponseEntity<ApiResponse<List<PaymentResponseDto>>> getPaymentsByReservation(@RequestParam Long reservationId) {
        ApiResponse<List<PaymentResponseDto>> allByReservationId = paymentService.getAllByReservationId(reservationId);
        return ResponseEntity.ok(allByReservationId);
    }
}
