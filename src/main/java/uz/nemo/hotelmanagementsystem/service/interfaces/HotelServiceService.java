package uz.nemo.hotelmanagementsystem.service.interfaces;

import org.springframework.data.domain.Pageable;
import uz.nemo.hotelmanagementsystem.dto.requests.HotelServiceRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.HotelServiceResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;

public interface HotelServiceService {
    ApiResponse<Void> create(HotelServiceRequestDto serviceRequestDTO);

    ApiResponse<HotelServiceResponseDto> getById(Long id);

    PaginationResponse getAll(Pageable pageable);

    ApiResponse<Void> update(Long id, HotelServiceRequestDto serviceRequestDTO);

    ApiResponse<Void> delete(Long id);
}
