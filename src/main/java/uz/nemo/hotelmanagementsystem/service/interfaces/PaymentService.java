package uz.nemo.hotelmanagementsystem.service.interfaces;

import java.util.List;
import uz.nemo.hotelmanagementsystem.dto.requests.PaymentRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.PaymentResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;

public interface PaymentService {
    ApiResponse<Void> process(PaymentRequestDto paymentRequestDTO);

    ApiResponse<PaymentResponseDto> getById(Long paymentId);

    ApiResponse<List<PaymentResponseDto>> getAllByUserId(Long userId);

    ApiResponse<List<PaymentResponseDto>> getAllByReservationId(Long bookingId);
}
