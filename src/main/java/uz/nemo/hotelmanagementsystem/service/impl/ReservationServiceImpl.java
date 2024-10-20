package uz.nemo.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nemo.hotelmanagementsystem.dto.requests.ReservationRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.ReservationResponseDto;
import uz.nemo.hotelmanagementsystem.entity.HotelInService;
import uz.nemo.hotelmanagementsystem.entity.Reservation;
import uz.nemo.hotelmanagementsystem.entity.Room;
import uz.nemo.hotelmanagementsystem.entity.User;
import uz.nemo.hotelmanagementsystem.exceptions.CustomIllegalArgumentException;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.mapper.ReservationMapper;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.repository.HotelServiceRepository;
import uz.nemo.hotelmanagementsystem.repository.ReservationRepository;
import uz.nemo.hotelmanagementsystem.repository.RoomRepository;
import uz.nemo.hotelmanagementsystem.repository.UserRepository;
import uz.nemo.hotelmanagementsystem.service.interfaces.ReservationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final HotelServiceRepository serviceRepository;

    @Override
    public ApiResponse<Void> create(ReservationRequestDto reservationDto) {
        roomRepository.findById(reservationDto.roomId()).orElseThrow(() ->
                new CustomNotFoundException("Room not found with id: " + reservationDto.roomId())
        );
        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(reservationDto.roomId(), reservationDto.checkInDate(), reservationDto.checkOutDate());
        if (!conflictingReservations.isEmpty()) {
            throw new CustomIllegalArgumentException("The room is already booked");
        }
        List<HotelInService> hotelServices = new ArrayList<>();
        if (!reservationDto.serviceIds().isEmpty()) {
            for (Long serviceId : reservationDto.serviceIds()) {
                Optional<HotelInService> serviceOptional = serviceRepository.findById(serviceId);
                serviceOptional.ifPresent(hotelServices::add);
            }
        }

        Reservation reservation = reservationMapper.mapToEntity(reservationDto);
        reservation.setServices(hotelServices);
        reservationRepository.save(reservation);
        return new ApiResponse<>().success();

    }

    @Override
    public ApiResponse<ReservationResponseDto> getById(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            throw new CustomNotFoundException("Reservation not found with id: " + reservationId);
        }
        ReservationResponseDto responseDto = reservationMapper.mapToResponse(reservationOptional.get());
        return new ApiResponse<ReservationResponseDto>().success(responseDto);

    }

    @Override
    public ApiResponse<Void> update(Long reservationId, ReservationRequestDto reservationDto) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            throw new CustomNotFoundException("Reservation not found with id: " + reservationId);
        }
        roomRepository.findById(reservationDto.roomId()).orElseThrow(() ->
                new CustomNotFoundException("Room not found with id: " + reservationDto.roomId())
        );
        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(reservationDto.roomId(), reservationDto.checkInDate(), reservationDto.checkOutDate());
        if (!conflictingReservations.isEmpty()) {
            throw new CustomIllegalArgumentException("The room is already booked");
        }
        Reservation reservation = reservationMapper.mapToEntity(reservationDto);
        reservation.setId(reservationId);
        reservationRepository.save(reservation);
        return new ApiResponse<>().success();


    }

    @Override
    public ApiResponse<Void> delete(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            throw new CustomNotFoundException("Reservation not found with id: " + reservationId);
        }
        reservationRepository.deleteById(reservationId);
        return new ApiResponse<>().success();

    }

    @Override
    public ApiResponse<List<ReservationResponseDto>> getAllByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new CustomNotFoundException("User not found with id: " + userId);
        }
        List<Reservation> reservationResponses = reservationRepository.findAllByUserId(userId);
        List<ReservationResponseDto> responseList = reservationMapper.mapToResponseList(reservationResponses);
        return new ApiResponse<List<ReservationResponseDto>>().success(responseList);

    }

    @Override
    public ApiResponse<List<ReservationResponseDto>> getAllByRoomId(Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isEmpty()) {
            throw new CustomNotFoundException("Room not found with id: " + roomId);
        }
        List<Reservation> reservationList = reservationRepository.findByRoomId(roomId);
        List<ReservationResponseDto> responseList = reservationMapper.mapToResponseList(reservationList);
        return new ApiResponse<List<ReservationResponseDto>>().success(responseList);

    }
}

