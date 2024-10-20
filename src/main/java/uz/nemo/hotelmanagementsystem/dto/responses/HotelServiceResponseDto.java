package uz.nemo.hotelmanagementsystem.dto.responses;

public record HotelServiceResponseDto(
        Long id,
        String name,
        String description,
        Double price){}
