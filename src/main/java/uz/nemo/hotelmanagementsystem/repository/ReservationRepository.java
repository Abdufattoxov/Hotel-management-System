package uz.nemo.hotelmanagementsystem.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nemo.hotelmanagementsystem.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("select r from Reservation r where r.user.id = :userId")
    List<Reservation> findAllByUserId(Long userId);

    @Query("select r from Reservation r where r.room.id = :roomId")
    List<Reservation> findByRoomId(Long roomId);

    @Query("select r from Reservation r where r.room.id = :roomId and (:checkInDate < r.checkOutDate AND :checkOutDate > r.checkInDate)")
    List<Reservation> findConflictingReservations(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);

    @Query("select r from Reservation r where r.checkOutDate = :today or r.checkInDate = :today ")
    List<Reservation> findAllByCheckInDateOrCheckOutDate(LocalDate today);
}
