package uz.nemo.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nemo.hotelmanagementsystem.dto.requests.HotelRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.HotelResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;
import uz.nemo.hotelmanagementsystem.service.interfaces.HotelService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotels")
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "Create a new Hotel", description = "Allows an admin to create a new hotel")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody HotelRequestDto hotelRequestDto){
        ApiResponse<Void> response = hotelService.create(hotelRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Hotel by ID", description = "Fetch a hotel by its ID. Accessible to admins and customers.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HotelResponseDto>> get(@PathVariable Long id){
        ApiResponse<HotelResponseDto> response = hotelService.get(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get All Hotels", description = "Retrieve a list of all hotels with pagination and sorting. Accessible to admins and customers.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping
    public ResponseEntity<PaginationResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "name") String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        PaginationResponse response = hotelService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a Hotel", description = "Allows an admin to update hotel information")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id, @Valid @RequestBody HotelRequestDto hotelRequestDto){
        ApiResponse<Void> response = hotelService.update(hotelRequestDto, id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a Hotel", description = "Allows an admin to delete a hotel by its ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        ApiResponse<Void> response = hotelService.delete(id);
        return ResponseEntity.ok(response);
    }
}
