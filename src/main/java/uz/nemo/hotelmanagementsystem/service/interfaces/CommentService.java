package uz.nemo.hotelmanagementsystem.service.interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.nemo.hotelmanagementsystem.dto.requests.CommentRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.CommentResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;

public interface CommentService {
    ApiResponse<CommentResponseDto> getById(Long id);

    PaginationResponse getAll(Long roomId, Pageable pageable);

    ApiResponse<Void> create(CommentRequestDto comment);

    ApiResponse<Void> delete(Long id);

    ApiResponse<Void> update(Long id, CommentRequestDto comment);
}
