package uz.nemo.hotelmanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginAttempt {
    @Id
    private String email;
    private int attempts;
    private LocalDateTime lockTime;
}
