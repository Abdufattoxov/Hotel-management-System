package uz.nemo.hotelmanagementsystem.dto.requests;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

public record ReservationRequestDto(
        @NotNull(message = "Customer ID is required")
        Long customerId,
        @NotNull(message = "Room ID is required")
        Long roomId,
        @NotNull(
        message = "Check-in date is required")
        @FutureOrPresent(message = "Check-in date must be today or in the future")
        LocalDate checkInDate,
        @NotNull(message = "Check-out date is required")
        @Future(message = "Check-out date must be in the future")
        LocalDate checkOutDate,
        List<Long> serviceIds
) {}
