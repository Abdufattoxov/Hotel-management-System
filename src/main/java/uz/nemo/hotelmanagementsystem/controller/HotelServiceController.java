package uz.nemo.hotelmanagementsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nemo.hotelmanagementsystem.dto.requests.HotelServiceRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.HotelServiceResponseDto;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;
import uz.nemo.hotelmanagementsystem.service.interfaces.HotelServiceService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotel-services")
public class HotelServiceController {

    private final HotelServiceService hotelService;

    @Operation(summary = "Create a new Hotel Service", description = "Allows an admin to create a new hotel service")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createService(@Valid @RequestBody HotelServiceRequestDto requestDto){
        ApiResponse<Void> response = hotelService.create(requestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Hotel Service by ID", description = "Fetch a hotel service by its ID. Accessible to admins and customers.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HotelServiceResponseDto>> getService(@PathVariable Long id){
        ApiResponse<HotelServiceResponseDto> response = hotelService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get All Hotel Services", description = "Retrieve a list of all hotel services with pagination and sorting. Accessible to admins and customers.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping
    public ResponseEntity<PaginationResponse> getAllServices(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "serviceName") String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        PaginationResponse responses = hotelService.getAll(pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Update a Hotel Service", description = "Allows an admin to update hotel service information")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateService(@PathVariable Long id,
                                                           @Valid @RequestBody HotelServiceRequestDto hotelServiceRequestDto){
        ApiResponse<Void> response = hotelService.update(id, hotelServiceRequestDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a Hotel Service", description = "Allows an admin to delete a hotel service by its ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Long id){
        ApiResponse<Void> response = hotelService.delete(id);
        return ResponseEntity.ok(response);
    }
}
