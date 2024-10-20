package uz.nemo.hotelmanagementsystem.dto.requests;

import javax.validation.constraints.NotNull;

public record PaymentRequestDto(
        @NotNull(
        message = "Reservation ID is required")
        Long reservationId,
        @NotNull(
        message = "Payment method is required")
        String paymentMethod,
        @NotNull(
        message = "Amount is required")
        Double amount) {
}