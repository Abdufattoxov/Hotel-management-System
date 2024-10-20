package uz.nemo.hotelmanagementsystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.nemo.hotelmanagementsystem.dto.requests.RoomRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.RoomResponseDto;
import uz.nemo.hotelmanagementsystem.entity.Room;
import uz.nemo.hotelmanagementsystem.entity.enums.RoomType;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.repository.HotelRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomMapper {
    private final HotelRepository hotelRepository;

    public Room mapToEntity(RoomRequestDto roomRequestDTO) {
        return Room.builder().
                roomNumber(roomRequestDTO.roomNumber())
                .roomType(RoomType.valueOf(roomRequestDTO.roomType().toUpperCase()))
                .price(roomRequestDTO.price())
                .isAvailable(roomRequestDTO.isAvailable())
                .hotel(hotelRepository.findById(roomRequestDTO.hotelId()).orElseThrow(() ->
                        new CustomNotFoundException("Hotel not found")))
                .build();
    }

    public RoomResponseDto mapToResponse(Room room) {
        return new RoomResponseDto(
                room.getId(),
                room.getRoomNumber(),
                String.valueOf(room.getRoomType()),
                room.getPrice(),
                room.getIsAvailable(),
                room.getHotel().getId());
    }

    public List<RoomResponseDto> mapToResponseList(List<Room> rooms) {
        return rooms.stream()
                .map(this::mapToResponse)
                .toList();
    }

}
