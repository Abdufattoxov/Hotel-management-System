package uz.nemo.hotelmanagementsystem.dto.responses;

public record CommentResponseDto(
        Long id,
        String message,
        Integer rank,
        String firstName) {
}
