package uz.nemo.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nemo.hotelmanagementsystem.dto.requests.CommentRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.CommentResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;
import uz.nemo.hotelmanagementsystem.service.interfaces.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Create Comment", description = "Allows a customer to create a comment")
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        ApiResponse<Void> response = commentService.create(commentRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Comment by ID", description = "Allows customers or admins to fetch a comment by its ID")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> getCommentById(@PathVariable Long id){
        ApiResponse<CommentResponseDto> response = commentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get All Comments", description = "Fetch all comments for a specific room with pagination and sorting")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<PaginationResponse> getAll(@RequestParam Long roomId,
                                                     @RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size,
                                                     @RequestParam(defaultValue = "message") String sortedBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortedBy));
        PaginationResponse responses = commentService.getAll(roomId, pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Update Comment", description = "Allows a customer to update their own comment")
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateComment(@PathVariable Long id, @Valid @RequestBody CommentRequestDto commentRequestDto){
        ApiResponse<Void> response = commentService.update(id, commentRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete Comment", description = "Allows a customer to delete their own comment")
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long id){
        ApiResponse<Void> response = commentService.delete(id);
        return ResponseEntity.ok(response);
    }
}
