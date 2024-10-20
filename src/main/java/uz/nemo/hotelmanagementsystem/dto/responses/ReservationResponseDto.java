package uz.nemo.hotelmanagementsystem.dto.responses;

import java.time.LocalDate;

public record ReservationResponseDto(
        Long id,
        UserResponseDto user,
        RoomResponseDto room,
        LocalDate checkInDate,
        LocalDate checkOutDate
) {}
