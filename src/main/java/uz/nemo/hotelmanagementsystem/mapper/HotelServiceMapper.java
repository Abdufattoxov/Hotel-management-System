package uz.nemo.hotelmanagementsystem.mapper;

import org.springframework.stereotype.Component;
import uz.nemo.hotelmanagementsystem.dto.requests.HotelServiceRequestDto;
import uz.nemo.hotelmanagementsystem.dto.responses.HotelServiceResponseDto;
import uz.nemo.hotelmanagementsystem.entity.HotelInService;

@Component
public class HotelServiceMapper {
    public HotelInService mapToEntity(HotelServiceRequestDto serviceRequestDTO) {
        return HotelInService.builder()
                .serviceName(serviceRequestDTO.name())
                .price(serviceRequestDTO.price())
                .description(serviceRequestDTO.description())
                .build();
    }

    public HotelServiceResponseDto mapToResponse(HotelInService service) {
        return new HotelServiceResponseDto(
                service.getId(),
                service.getServiceName(),
                service.getDescription(),
                service.getPrice());
    }
}
