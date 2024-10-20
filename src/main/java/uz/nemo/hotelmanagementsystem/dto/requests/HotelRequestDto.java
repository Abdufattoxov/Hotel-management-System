package uz.nemo.hotelmanagementsystem.dto.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record HotelRequestDto(
        @NotNull(message = "Hotel name is required")
        @Size(min = 3, max = 100, message = "Hotel name must be between 3 and 100 characters")
        String name,
        @NotNull(message = "Location is required")
        @Size(min = 5, max = 255, message = "Location must be between 5 and 255 characters")
        String location
) {}
