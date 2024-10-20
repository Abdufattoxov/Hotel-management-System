package uz.nemo.hotelmanagementsystem.dto.responses;

public record RoomResponseDto(
        Long id,
        String roomNumber,
        String roomType,
        Double price,
        Boolean isAvailable,
        Long hotelId
) {}
