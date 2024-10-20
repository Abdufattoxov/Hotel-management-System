package uz.nemo.hotelmanagementsystem.service.impl;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.nemo.hotelmanagementsystem.dto.requests.CommentRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.CommentResponseDto;
import uz.nemo.hotelmanagementsystem.entity.Comment;
import uz.nemo.hotelmanagementsystem.entity.Room;
import uz.nemo.hotelmanagementsystem.entity.User;
import uz.nemo.hotelmanagementsystem.exceptions.CustomBadRequestException;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.mapper.CommentMapper;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;
import uz.nemo.hotelmanagementsystem.repository.CommentRepository;
import uz.nemo.hotelmanagementsystem.repository.RoomRepository;
import uz.nemo.hotelmanagementsystem.repository.UserRepository;
import uz.nemo.hotelmanagementsystem.service.interfaces.CommentService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    public ApiResponse<CommentResponseDto> getById(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new CustomNotFoundException("Comment not found with id: " + id);
        }
        CommentResponseDto responseDto = commentMapper.mapToResponse(commentOptional.get());
        return new ApiResponse<CommentResponseDto>().success(responseDto);

    }

    @Override
    public PaginationResponse getAll(Long roomId, Pageable pageable) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isEmpty()) {
            throw new CustomNotFoundException("Room not found with id: " + roomId);
        }
        Page<CommentResponseDto> allComment = commentRepository.findAllByRoomId(roomId, pageable);
        return new PaginationResponse(allComment.getTotalPages(), allComment.getContent());
    }

    @Override
    public ApiResponse<Void> create(CommentRequestDto commentDto) {
        Optional<User> userOptional = userRepository.findById(commentDto.userId());
        if (userOptional.isEmpty()) {
            throw new CustomBadRequestException("User not found with id: " + commentDto.userId());
        }
        Optional<Room> roomOptional = roomRepository.findById(commentDto.roomId());
        if (roomOptional.isEmpty()) {
            throw new CustomBadRequestException("Room not found with id: " + commentDto.roomId());
        }
        if (commentRepository.isExistsByUserIdAndProductId(commentDto.userId(), commentDto.roomId())) {
            throw new CustomBadRequestException("User already wrote a comment");
        }
        Comment comment = commentMapper.mapToEntity(commentDto);
        comment.setUser(userOptional.get());
        comment.setRoom(roomOptional.get());
        commentRepository.save(comment);
        return new ApiResponse<>().success();
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new CustomBadRequestException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
        return new ApiResponse<>().success();

    }

    @Override
    public ApiResponse<Void> update(Long id, CommentRequestDto commentDto) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            throw new CustomBadRequestException("Comment not found with id: " + id);
        }
        Comment comment = commentOptional.get();
        if (!comment.getUser().getId().equals(commentDto.userId())) {
            throw new CustomBadRequestException("Wrong user id: " + commentDto.userId());
        }
        comment.setMessage(commentDto.message());
        comment.setRank(commentDto.rank());
        commentRepository.save(comment);
        return new ApiResponse<>().success();


    }
}
