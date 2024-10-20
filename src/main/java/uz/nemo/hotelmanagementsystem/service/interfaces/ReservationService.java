package uz.nemo.hotelmanagementsystem.service.interfaces;

import java.util.List;
import uz.nemo.hotelmanagementsystem.dto.requests.ReservationRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.ReservationResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;

public interface ReservationService {
    ApiResponse<Void> create(ReservationRequestDto reservationRequestDto);

    ApiResponse<ReservationResponseDto> getById(Long reservationId);

    ApiResponse<Void> update(Long reservationId, ReservationRequestDto reservationDto);

    ApiResponse<Void> delete(Long reservationId);

    ApiResponse<List<ReservationResponseDto>> getAllByUserId(Long userId);

    ApiResponse<List<ReservationResponseDto>> getAllByRoomId(Long roomId);
}
