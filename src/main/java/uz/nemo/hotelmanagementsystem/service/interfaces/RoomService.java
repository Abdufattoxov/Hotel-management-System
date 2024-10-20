package uz.nemo.hotelmanagementsystem.service.interfaces;

import java.time.LocalDate;
import org.springframework.data.domain.Pageable;
import uz.nemo.hotelmanagementsystem.dto.requests.RoomRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.RoomResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;

public interface RoomService {
    ApiResponse<Void> create(RoomRequestDto roomRequestDTO);

    ApiResponse<RoomResponseDto> getById(Long id);

    PaginationResponse getAll(Pageable pageable);

    ApiResponse<Void> update(Long id, RoomRequestDto roomRequestDTO);

    ApiResponse<Void> delete(Long id);

    PaginationResponse findAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, Pageable pageable);
}
