package uz.nemo.hotelmanagementsystem.dto.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record RoomRequestDto(
        @NotNull(message = "Room number is required")
        @Size(min = 1, max = 10, message = "Room number must be between 1 and 10 characters")
        String roomNumber,
        @NotNull(message = "Room type is required")
        String roomType,
        @NotNull(message = "Price is required")
        Double price,
        @NotNull(message = "Availability status is required")
        Boolean isAvailable,
        @NotNull(message = "Hotel id is required")
        Long hotelId) {}