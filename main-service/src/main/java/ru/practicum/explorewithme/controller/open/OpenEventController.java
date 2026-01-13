package ru.practicum.explorewithme.controller.open;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.event.EventFullDto;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.StatsService;
import ru.practicum.explorewithme.util.DateTimePattern;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class OpenEventController {

    private final EventService eventService;
    private final StatsService statsService;

    @GetMapping("/events")
    public List<EventShortDto> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = DateTimePattern.DATE_TIME) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DateTimePattern.DATE_TIME) LocalDateTime rangeEnd,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") @Min(0) Integer from,
            @RequestParam(defaultValue = "10") @Min(1) Integer size,
            HttpServletRequest request) {

        if ((rangeStart == null && rangeEnd != null) || (rangeStart != null && rangeEnd == null)) {
            throw new IllegalArgumentException("Both rangeStart and rangeEnd must be specified together");
        }

        statsService.saveHit("main-service", request.getRequestURI(), request.getRemoteAddr());
        return eventService.getEventsByPublic(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(
            @PathVariable Long id,
            HttpServletRequest request) {

        statsService.saveHit("main-service", request.getRequestURI(), request.getRemoteAddr());
        return eventService.getEventByPublic(id);
    }
}