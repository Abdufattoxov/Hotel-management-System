package uz.nemo.hotelmanagementsystem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nemo.hotelmanagementsystem.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select p from Payment p where p.reservation.user.id = :userId")
    List<Payment> findAllByUserId(Long userId);

    @Query("select p from Payment p where p.reservation.id = :reservationId")
    List<Payment> findAllByReservationId(Long reservationId);
}
