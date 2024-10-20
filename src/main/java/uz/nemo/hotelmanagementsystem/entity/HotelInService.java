package uz.nemo.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HotelInService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}

