package uz.nemo.hotelmanagementsystem.service.interfaces;

import org.springframework.data.domain.Pageable;
import uz.nemo.hotelmanagementsystem.dto.requests.HotelRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.HotelResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;

public interface HotelService {
    ApiResponse<Void> create(HotelRequestDto hotelRequestDto);

    ApiResponse<Void> update(HotelRequestDto hotelRequestDto, Long id);

    ApiResponse<Void> delete(Long id);

    ApiResponse<HotelResponseDto> get(Long id);

    PaginationResponse getAll(Pageable pageable);
}
