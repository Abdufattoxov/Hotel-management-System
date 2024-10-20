package uz.nemo.hotelmanagementsystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.nemo.hotelmanagementsystem.dto.requests.ReservationRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.ReservationResponseDto;
import uz.nemo.hotelmanagementsystem.entity.Reservation;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.repository.RoomRepository;
import uz.nemo.hotelmanagementsystem.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationMapper {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoomMapper roomMapper;

    public Reservation mapToEntity(ReservationRequestDto reservationRequestDto) {
        return Reservation.builder()
                .room(roomRepository.findById(reservationRequestDto.roomId()).orElseThrow(() ->
                        new CustomNotFoundException("Room not found with id: " + reservationRequestDto.roomId())
                ))
                .user(userRepository.findById(reservationRequestDto.customerId()).orElseThrow(() ->
                        new CustomNotFoundException("User not found with id: " + reservationRequestDto.customerId())
                ))
                .checkInDate(reservationRequestDto.checkInDate())
                .checkOutDate(reservationRequestDto.checkOutDate())
                .build();
    }

    public ReservationResponseDto mapToResponse(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                userMapper.mapToResponse(reservation.getUser()),
                roomMapper.mapToResponse(reservation.getRoom()),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate());
    }

    public List<ReservationResponseDto> mapToResponseList(List<Reservation> reservationResponses) {
        return reservationResponses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


}

