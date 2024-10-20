package uz.nemo.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.nemo.hotelmanagementsystem.dto.requests.HotelRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.HotelResponseDto;
import uz.nemo.hotelmanagementsystem.entity.Hotel;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.mapper.HotelMapper;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;
import uz.nemo.hotelmanagementsystem.repository.HotelRepository;
import uz.nemo.hotelmanagementsystem.service.interfaces.HotelService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;

    @Override
    public ApiResponse<Void> create(HotelRequestDto hotelRequestDto) {
        Hotel hotel = hotelMapper.mapToEntity(hotelRequestDto);
        hotelRepository.save(hotel);
        return new ApiResponse<>().success();
    }

    @Override
    public ApiResponse<Void> update(HotelRequestDto hotelRequestDto, Long id) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isEmpty()) {
            throw new CustomNotFoundException("Hotel not found with id: " + id);
        }
        Hotel hotel = hotelOptional.get();
        hotel.setName(hotelRequestDto.name());
        hotel.setLocation(hotelRequestDto.location());
        hotelRepository.save(hotel);
        return new ApiResponse<>().success();

    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isEmpty()) {
            throw new CustomNotFoundException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
        return new ApiResponse<>().success();

    }

    @Override
    public ApiResponse<HotelResponseDto> get(Long id) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isEmpty()) {
            throw new CustomNotFoundException("Hotel not found with id: " + id);
        }
        HotelResponseDto response = hotelMapper.mapToResponse(hotelOptional.get());
        return new ApiResponse<HotelResponseDto>().success(response);

    }

    @Override
    public PaginationResponse getAll(Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findAll(pageable);
        return new PaginationResponse(hotels.getTotalPages(), hotels.getContent());
    }

}
