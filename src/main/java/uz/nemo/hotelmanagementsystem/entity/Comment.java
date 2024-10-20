package uz.nemo.hotelmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Integer rank;
    @ManyToOne
    private Room room;
    @ManyToOne
    private User user;
}
