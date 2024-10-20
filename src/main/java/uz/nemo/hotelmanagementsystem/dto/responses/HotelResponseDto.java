package uz.nemo.hotelmanagementsystem.dto.responses;

import java.util.List;

public record HotelResponseDto(
        Long id,
        String name,
        String location,
        List<RoomResponseDto> rooms) {}