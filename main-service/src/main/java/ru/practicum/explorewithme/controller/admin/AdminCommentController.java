package ru.practicum.explorewithme.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.UpdateCommentAdminRequest;
import ru.practicum.explorewithme.model.CommentStatus;
import ru.practicum.explorewithme.service.CommentService;
import ru.practicum.explorewithme.util.DateTimePattern;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentService commentService;

    @PatchMapping("/admin/comments/{commentId}")
    public CommentDto updateCommentByAdmin(
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentAdminRequest updateRequest) {

        return commentService.updateCommentByAdmin(commentId, updateRequest);
    }

    @GetMapping("/admin/comments")
    public List<CommentDto> getCommentsByAdmin(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<Long> events,
            @RequestParam(required = false) List<CommentStatus> statuses,
            @RequestParam(required = false) @DateTimeFormat(pattern = DateTimePattern.DATE_TIME) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DateTimePattern.DATE_TIME) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {

        return commentService.getCommentsByAdmin(
                users, events, statuses, rangeStart, rangeEnd, from, size);
    }
}