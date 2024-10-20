package uz.nemo.hotelmanagementsystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.nemo.hotelmanagementsystem.dto.requests.PaymentRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.PaymentResponseDto;
import uz.nemo.hotelmanagementsystem.entity.Payment;
import uz.nemo.hotelmanagementsystem.entity.enums.PaymentMethod;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentMapper {
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;

    public PaymentResponseDto mapToResponse(Payment payment) {
        return new PaymentResponseDto(
                payment.getId(),
                reservationMapper.mapToResponse(payment.getReservation()),
                payment.getPaymentMethod().toString(),
                payment.getAmount(),
                payment.getPaymentDate());
    }

    public List<PaymentResponseDto> mapToResponseList(List<Payment> payments) {
        return payments.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Payment mapToEntity(PaymentRequestDto paymentRequestDTO) {
        return Payment.builder()
                .reservation(reservationRepository.findById(paymentRequestDTO.reservationId()).orElseThrow(
                        () ->  new CustomNotFoundException("Reservation not found with id: "+ paymentRequestDTO.reservationId())
                ))
                .paymentDate(LocalDateTime.now())
                .amount(paymentRequestDTO.amount())
                .paymentMethod(PaymentMethod.valueOf(paymentRequestDTO.paymentMethod().toUpperCase()))
                .build();
    }

}

