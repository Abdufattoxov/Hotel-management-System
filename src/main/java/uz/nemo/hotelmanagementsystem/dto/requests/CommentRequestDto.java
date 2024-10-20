package uz.nemo.hotelmanagementsystem.dto.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public record CommentRequestDto(
        @NotNull(message = "Comment message can't be null")
        String message,
        @Max(5L) Integer rank,
        Long roomId,
        Long userId)
{}
