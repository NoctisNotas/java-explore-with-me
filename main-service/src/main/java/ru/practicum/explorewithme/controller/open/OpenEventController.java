package ru.practicum.explorewithme.controller.open;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OpenEventController {

    private final EventService eventService;
    private final StatsService statsService;

    @GetMapping("/events")
    public List<EventShortDto> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {

        LocalDateTime start = null;
        LocalDateTime end = null;

        try {
            if (rangeStart != null) {
                start = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern(DateTimePattern.DATE_TIME));
            }
            if (rangeEnd != null) {
                end = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern(DateTimePattern.DATE_TIME));
            }
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format. Use yyyy-MM-dd HH:mm:ss");
        }

        statsService.saveHit("main-service", request.getRequestURI(), request.getRemoteAddr());
        return eventService.getEventsByPublic(
                text, categories, paid, start, end, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(
            @PathVariable Long id,
            HttpServletRequest request) {

        statsService.saveHit("main-service", request.getRequestURI(), request.getRemoteAddr());
        return eventService.getEventByPublic(id);
    }
}