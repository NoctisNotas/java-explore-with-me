package ru.practicum.explorewithme.controller.open;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.service.CommentService;
import ru.practicum.explorewithme.service.StatsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OpenCommentController {

    private final CommentService commentService;
    private final StatsService statsService;

    @GetMapping("/events/{eventId}/comments")
    public List<CommentDto> getEventComments(
            @PathVariable Long eventId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {

        statsService.saveHit("main-service", request.getRequestURI(), request.getRemoteAddr());
        return commentService.getEventComments(eventId, from, size);
    }
}