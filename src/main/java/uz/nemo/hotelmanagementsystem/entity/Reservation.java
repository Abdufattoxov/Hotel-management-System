package uz.nemo.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>() ;

    @OneToMany( mappedBy = "reservation", cascade = {CascadeType.ALL})
    private List<HotelInService> services = new ArrayList<>();
}

