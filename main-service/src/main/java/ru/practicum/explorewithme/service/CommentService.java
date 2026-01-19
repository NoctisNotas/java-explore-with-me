package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.NewCommentDto;
import ru.practicum.explorewithme.dto.comment.UpdateCommentAdminRequest;
import ru.practicum.explorewithme.dto.comment.UpdateCommentRequest;
import ru.practicum.explorewithme.model.CommentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {

    CommentDto createComment(Long userId, NewCommentDto newCommentDto);

    CommentDto updateCommentByUser(Long userId, Long commentId, UpdateCommentRequest updateRequest);

    void deleteCommentByUser(Long userId, Long commentId);

    List<CommentDto> getEventComments(Long eventId, Integer from, Integer size);

    CommentDto updateCommentByAdmin(Long commentId, UpdateCommentAdminRequest updateRequest);

    List<CommentDto> getCommentsByAdmin(
            List<Long> users,
            List<Long> events,
            List<CommentStatus> statuses,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size);
}