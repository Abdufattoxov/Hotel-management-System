package uz.nemo.hotelmanagementsystem.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nemo.hotelmanagementsystem.dto.responses.UserResponseDto;
import uz.nemo.hotelmanagementsystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
            select new uz.nemo.hotelmanagementsystem.dto.responses.UserResponseDto(
            u.id,
            u.firstName,
            u.lastName,
            u.email,
            u.phoneNumber
            ) from User u
            """)
    Page<UserResponseDto> findAllUserResponse(Pageable pageable);

    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationToken(String token);

    Optional<User> findByResetToken(String token);
}
