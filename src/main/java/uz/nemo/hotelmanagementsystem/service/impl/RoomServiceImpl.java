package uz.nemo.hotelmanagementsystem.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.nemo.hotelmanagementsystem.dto.requests.RoomRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.RoomResponseDto;
import uz.nemo.hotelmanagementsystem.entity.Room;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.mapper.RoomMapper;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;
import uz.nemo.hotelmanagementsystem.repository.RoomRepository;
import uz.nemo.hotelmanagementsystem.service.interfaces.RoomService;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public ApiResponse<Void> create(RoomRequestDto roomRequestDTO) {
        Room room = roomMapper.mapToEntity(roomRequestDTO);
        roomRepository.save(room);
        return new ApiResponse<>().success();
    }

    public ApiResponse<RoomResponseDto> getById(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isEmpty()) {
            throw new CustomNotFoundException("Room not found with id: " + id);
        }
        RoomResponseDto responseDto = roomMapper.mapToResponse(roomOptional.get());
        return new ApiResponse<RoomResponseDto>().success(responseDto);

    }

    public PaginationResponse getAll(Pageable pageable) {
        Page<RoomResponseDto> allRoomResponses = roomRepository.findAllRoomResponses(pageable);
        return new PaginationResponse(allRoomResponses.getTotalPages(), allRoomResponses.getContent());
    }

    public ApiResponse<Void> update(Long id, RoomRequestDto roomRequestDTO) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isEmpty()) {
            throw new CustomNotFoundException("Room not found with id: " + id);
        }
        Room room = roomMapper.mapToEntity(roomRequestDTO);
        room.setId(id);
        roomRepository.save(room);
        return new ApiResponse<>().success();

    }

    public ApiResponse<Void> delete(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isEmpty()) {
            throw new CustomNotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
        return new ApiResponse<>().success();

    }

    public PaginationResponse findAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, Pageable pageable) {
        Page<RoomResponseDto> response = roomRepository.findAvailableRooms(checkInDate, checkOutDate, pageable);
        return new PaginationResponse(response.getTotalPages(), response.getContent());
    }
}

