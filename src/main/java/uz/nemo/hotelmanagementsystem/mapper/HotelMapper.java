package uz.nemo.hotelmanagementsystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.nemo.hotelmanagementsystem.dto.requests.HotelRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.HotelResponseDto;
import uz.nemo.hotelmanagementsystem.entity.Hotel;

@Component
@RequiredArgsConstructor
public class HotelMapper {
    private final RoomMapper roomMapper;

    public Hotel mapToEntity(HotelRequestDto hotelRequestDto) {
        return Hotel.builder()
                .name(hotelRequestDto.name())
                .location(hotelRequestDto.location())
                .build();
    }

    public HotelResponseDto mapToResponse(Hotel hotel) {
        return new HotelResponseDto(
                hotel.getId(),
                hotel.getName(),
                hotel.getLocation(),
                roomMapper.mapToResponseList(hotel.getRooms()));
    }

}
