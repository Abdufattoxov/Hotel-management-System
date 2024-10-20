package uz.nemo.hotelmanagementsystem.utlity;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.nemo.hotelmanagementsystem.entity.Reservation;
import uz.nemo.hotelmanagementsystem.entity.Room;
import uz.nemo.hotelmanagementsystem.repository.ReservationRepository;
import uz.nemo.hotelmanagementsystem.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomStatusUpdater {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateRoomStatus() {
        LocalDate today = LocalDate.now();

        List<Reservation> reservations = reservationRepository.findAllByCheckInDateOrCheckOutDate(today);

        for (Reservation reservation : reservations) {
            Room room = reservation.getRoom();
            if (reservation.getCheckInDate().equals(today)) {
                room.setIsAvailable(false);
            }
            if (reservation.getCheckOutDate().equals(today)) {
                room.setIsAvailable(true);
            }
            roomRepository.save(room);
        }
    }
}

