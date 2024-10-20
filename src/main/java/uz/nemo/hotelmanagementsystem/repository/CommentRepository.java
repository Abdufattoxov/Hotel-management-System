package uz.nemo.hotelmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nemo.hotelmanagementsystem.dto.responses.CommentResponseDto;
import uz.nemo.hotelmanagementsystem.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
            select new uz.nemo.hotelmanagementsystem.dto.responses.CommentResponseDto(
            c.id,
            c.message,
            c.rank,
            c.user.firstName
            ) from Comment c where c.room.id = :roomId
            """)
    Page<CommentResponseDto> findAllByRoomId(Long roomId, Pageable pageable);

    @Query("select case when count(c) > 0 then true else false end from Comment c where c.user.id = :userId and c.room.id = :roomId")
    boolean isExistsByUserIdAndProductId(Long userId, Long roomId);
}
