package uz.nemo.hotelmanagementsystem.dto.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record HotelServiceRequestDto(
        @NotNull(message = "Service name is required")
        @Size(min = 3, max = 100, message = "Service name must be between 3 and 100 characters")
        String name,
        @NotNull(message = "Service description is required")
        @Size(min = 10,max = 255,message = "Description must be between 10 and 255 characters")
        String description,
        @NotNull(message = "Service price is required")
        double price
) {}
