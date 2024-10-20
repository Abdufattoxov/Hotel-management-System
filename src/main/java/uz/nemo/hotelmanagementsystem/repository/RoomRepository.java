package uz.nemo.hotelmanagementsystem.repository;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nemo.hotelmanagementsystem.dto.responses.RoomResponseDto;
import uz.nemo.hotelmanagementsystem.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("""
            select new uz.nemo.hotelmanagementsystem.dto.responses.RoomResponseDto(
            r.id,
            r.roomNumber,
            cast(r.roomType as string),
            r.price,
            r.isAvailable,
            r.hotel.id
            ) from Room r
            """)
    Page<RoomResponseDto> findAllRoomResponses(Pageable pageable);


    @Query("""
            select new uz.nemo.hotelmanagementsystem.dto.responses.RoomResponseDto(
            r.id,
            r.roomNumber,
            cast(r.roomType as string),
            r.price,
            r.isAvailable,
            r.hotel.id
            ) from Room r where r.isAvailable=true and r.id not in (select res.room.id from Reservation res where (res.checkInDate < :checkOutDate and res.checkOutDate > :checkInDate))
            """)
    Page<RoomResponseDto> findAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, Pageable pageable);
}
