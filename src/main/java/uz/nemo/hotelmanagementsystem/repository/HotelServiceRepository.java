package uz.nemo.hotelmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nemo.hotelmanagementsystem.dto.responses.HotelServiceResponseDto;
import uz.nemo.hotelmanagementsystem.entity.HotelInService;

@Repository
public interface HotelServiceRepository extends JpaRepository<HotelInService, Long> {
    @Query("""
            select new uz.nemo.hotelmanagementsystem.dto.responses.HotelServiceResponseDto(
            h.id,
            h.serviceName,
            h.description,
            h.price
            ) from HotelInService h
            """)
    Page<HotelServiceResponseDto> findAllResponseDto(Pageable pageable);
}
