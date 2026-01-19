package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.CommentStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByEventIdAndStatus(Long eventId, CommentStatus status, Pageable pageable);

    List<Comment> findByAuthorId(Long authorId, Pageable pageable);

    Optional<Comment> findByIdAndAuthorId(Long commentId, Long authorId);

    @Query("SELECT c FROM Comment c WHERE " +
            "(:users IS NULL OR c.author.id IN :users) " +
            "AND (:events IS NULL OR c.event.id IN :events) " +
            "AND (:statuses IS NULL OR c.status IN :statuses) " +
            "AND (:rangeStart IS NULL OR c.created >= :rangeStart) " +
            "AND (:rangeEnd IS NULL OR c.created <= :rangeEnd)")
    List<Comment> findCommentsByAdmin(
            @Param("users") List<Long> users,
            @Param("events") List<Long> events,
            @Param("statuses") List<CommentStatus> statuses,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable);
}