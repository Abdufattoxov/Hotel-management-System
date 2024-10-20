package uz.nemo.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nemo.hotelmanagementsystem.dto.requests.PaymentRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.PaymentResponseDto;
import uz.nemo.hotelmanagementsystem.entity.*;
import uz.nemo.hotelmanagementsystem.exceptions.CustomBadRequestException;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.mapper.PaymentMapper;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.repository.PaymentRepository;
import uz.nemo.hotelmanagementsystem.repository.ReservationRepository;
import uz.nemo.hotelmanagementsystem.repository.RoomRepository;
import uz.nemo.hotelmanagementsystem.repository.UserRepository;
import uz.nemo.hotelmanagementsystem.service.interfaces.PaymentService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;

    public ApiResponse<Void> process(PaymentRequestDto paymentRequestDTO) {
        Reservation reservation = reservationRepository.findById(paymentRequestDTO.reservationId()).orElseThrow(() ->
                new CustomNotFoundException("Reservation not found with id: " + paymentRequestDTO.reservationId())
        );
        double totalAmount = calculateTotalAmount(reservation);
        if (paymentRequestDTO.amount() < totalAmount) {
            throw new CustomBadRequestException("Amount of price is less. Total amount is: " + totalAmount);
        }
        Payment payment = paymentMapper.mapToEntity(paymentRequestDTO);
        payment.setAmount(totalAmount);
        paymentRepository.save(payment);

        Room room = roomRepository.findById(reservation.getRoom().getId()).orElseThrow(() ->
                new CustomNotFoundException("Room not found with id: " + reservation.getRoom().getId())
        );
        if (reservation.getCheckInDate().isEqual(LocalDate.now())) {
            room.setIsAvailable(false);
            roomRepository.save(room);
        }

        List<Payment> payments = reservation.getPayments();
        payments.add(payment);
        reservation.setPayments(payments);
        reservationRepository.save(reservation);
        return new ApiResponse<>().success();

    }

    public ApiResponse<PaymentResponseDto> getById(Long paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isEmpty()) {
            throw new CustomNotFoundException("Payment not found with id: " + paymentId);
        }
        PaymentResponseDto responseDto = paymentMapper.mapToResponse(paymentOptional.get());
        return new ApiResponse<PaymentResponseDto>().success(responseDto);

    }

    public ApiResponse<List<PaymentResponseDto>> getAllByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException("User not found with id: " + userId);
        }
        List<Payment> payments = paymentRepository.findAllByUserId(userId);
        List<PaymentResponseDto> responseDto = paymentMapper.mapToResponseList(payments);
        return new ApiResponse<List<PaymentResponseDto>>().success(responseDto);

    }

    public ApiResponse<List<PaymentResponseDto>> getAllByReservationId(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            throw new CustomNotFoundException("Reservation not found with id: " + reservationId);
        }
        List<Payment> payments = paymentRepository.findAllByReservationId(reservationId);
        List<PaymentResponseDto> responseDto = paymentMapper.mapToResponseList(payments);
        return new ApiResponse<List<PaymentResponseDto>>().success(responseDto);

    }

    private double calculateTotalAmount(Reservation reservation) {
        long totalDays = ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
        double totalAmountOfRooms = reservation.getRoom().getPrice() * (double) totalDays;
        double totalServicePrice = 0.0;

        for (HotelInService service : reservation.getServices()) {
            totalServicePrice += service.getPrice();
        }

        return totalAmountOfRooms + totalServicePrice;
    }
}