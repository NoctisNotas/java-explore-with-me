package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByRequesterId(Long userId);

    List<Request> findByEventId(Long eventId);

    List<Request> findByIdIn(List<Long> requestIds);

    Optional<Request> findByEventIdAndRequesterId(Long eventId, Long userId);

    Long countByEventIdAndStatus(Long eventId, RequestStatus status);

    List<Request> findByEventIdAndStatus(Long eventId, RequestStatus status);
}