package uz.nemo.hotelmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.nemo.hotelmanagementsystem.dto.requests.HotelServiceRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.HotelServiceResponseDto;
import uz.nemo.hotelmanagementsystem.entity.HotelInService;
import uz.nemo.hotelmanagementsystem.exceptions.CustomNotFoundException;
import uz.nemo.hotelmanagementsystem.mapper.HotelServiceMapper;
import uz.nemo.hotelmanagementsystem.payload.ApiResponse;
import uz.nemo.hotelmanagementsystem.payload.PaginationResponse;
import uz.nemo.hotelmanagementsystem.repository.HotelServiceRepository;
import uz.nemo.hotelmanagementsystem.service.interfaces.HotelServiceService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelServiceServiceImpl implements HotelServiceService {
    private final HotelServiceRepository serviceRepository;
    private final HotelServiceMapper serviceMapper;

    @Override
    public ApiResponse<Void> create(HotelServiceRequestDto serviceRequestDTO) {
        HotelInService service = serviceMapper.mapToEntity(serviceRequestDTO);
        serviceRepository.save(service);
        return new ApiResponse<>().success();
    }

    @Override
    public ApiResponse<HotelServiceResponseDto> getById(Long id) {
        Optional<HotelInService> hotelServiceOptional = serviceRepository.findById(id);
        if (hotelServiceOptional.isEmpty()) {
            throw new CustomNotFoundException("Hotel service not found with id: " + id);
        }
        HotelInService service = hotelServiceOptional.get();
        HotelServiceResponseDto responseDto = serviceMapper.mapToResponse(service);
        return new ApiResponse<HotelServiceResponseDto>().success(responseDto);

    }

    @Override
    public PaginationResponse getAll(Pageable pageable) {
        Page<HotelServiceResponseDto> serviceResponse = serviceRepository.findAllResponseDto(pageable);
        return new PaginationResponse(serviceResponse.getTotalPages(), serviceResponse.getContent());
    }

    @Override
    public ApiResponse<Void> update(Long id, HotelServiceRequestDto serviceRequestDTO) {
        Optional<HotelInService> hotelServiceOptional = serviceRepository.findById(id);
        if (hotelServiceOptional.isEmpty()) {
            throw new CustomNotFoundException("Hotel service not found with id: " + id);
        }
        HotelInService service = serviceMapper.mapToEntity(serviceRequestDTO);
        service.setId(id);
        serviceRepository.save(service);
        return new ApiResponse<>().success();

    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        Optional<HotelInService> hotelServiceOptional = serviceRepository.findById(id);
        if (hotelServiceOptional.isEmpty()) {
            throw new CustomNotFoundException("Hotel service not found with id: " + id);
        }
        serviceRepository.deleteById(id);
        return new ApiResponse<>().success();

    }
}

