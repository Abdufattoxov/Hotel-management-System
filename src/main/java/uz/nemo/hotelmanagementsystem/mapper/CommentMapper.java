package uz.nemo.hotelmanagementsystem.mapper;

import org.springframework.stereotype.Component;
import uz.nemo.hotelmanagementsystem.dto.requests.CommentRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.CommentResponseDto;
import uz.nemo.hotelmanagementsystem.entity.Comment;

@Component
public class CommentMapper {
    public CommentResponseDto mapToResponse(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getMessage(),
                comment.getRank(),
                comment.getUser().getFirstName());
    }

    public Comment mapToEntity(CommentRequestDto commentDto) {
        return Comment.builder()
                .message(commentDto.message())
                .rank(commentDto.rank())
                .build();
    }
}
