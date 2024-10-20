package uz.nemo.hotelmanagementsystem.dto.responses;

import java.time.LocalDateTime;

public record PaymentResponseDto(
        Long id,
        ReservationResponseDto reservation,
        String paymentMethod,
        Double amount,
        LocalDateTime paymentDate) {}
