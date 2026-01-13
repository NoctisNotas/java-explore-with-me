package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByInitiatorId(Long userId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long userId);

    @Query("SELECT e FROM Event e " +
            "WHERE (:users IS NULL OR e.initiator.id IN :users) " +
            "AND (:states IS NULL OR e.state IN :states) " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (COALESCE(:rangeStart, e.eventDate) <= e.eventDate) " +
            "AND (COALESCE(:rangeEnd, e.eventDate) >= e.eventDate)")
    List<Event> findEventsByAdmin(
            List<Long> users,
            List<EventState> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.state = 'PUBLISHED' " +
            "AND (:text IS NULL OR :text = '' OR " +
            "     LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
            "     LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))) " +
            "AND (:categories IS NULL OR e.category.id IN :categories)")
    List<Event> findPublishedEvents(
            @Param("text") String text,
            @Param("categories") List<Long> categories,
            Pageable pageable);

    Long countByCategoryId(Long categoryId);
}