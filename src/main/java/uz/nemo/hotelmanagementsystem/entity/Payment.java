package uz.nemo.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.nemo.hotelmanagementsystem.entity.enums.PaymentMethod;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private PaymentMethod paymentMethod;
}

