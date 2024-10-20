package uz.nemo.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.nemo.hotelmanagementsystem.entity.enums.RoomType;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    @ManyToOne
    private Hotel hotel;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private Double price;
    private Boolean isAvailable;
    @OneToMany(mappedBy = "room", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}
