package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.NewCommentDto;
import ru.practicum.explorewithme.dto.comment.UpdateCommentAdminRequest;
import ru.practicum.explorewithme.dto.comment.UpdateCommentRequest;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.mapper.CommentMapper;
import ru.practicum.explorewithme.model.*;
import ru.practicum.explorewithme.repository.CommentRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CommentDto createComment(Long userId, NewCommentDto newCommentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Event event = eventRepository.findById(newCommentDto.getEventId())
                .orElseThrow(() -> new NotFoundException("Event not found"));

        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Cannot comment unpublished event");
        }

        Comment comment = CommentMapper.toComment(newCommentDto);
        comment.setAuthor(user);
        comment.setEvent(event);

        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toCommentDto(savedComment);
    }

    @Override
    @Transactional
    public CommentDto updateCommentByUser(Long userId, Long commentId, UpdateCommentRequest updateRequest) {
        Comment comment = commentRepository.findByIdAndAuthorId(commentId, userId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        if (comment.getStatus() == CommentStatus.DELETED) {
            throw new ConflictException("Cannot edit deleted comment");
        }

        comment.setText(updateRequest.getText());
        comment.setStatus(CommentStatus.PENDING);

        Comment updatedComment = commentRepository.save(comment);
        return CommentMapper.toCommentDto(updatedComment);
    }

    @Override
    @Transactional
    public void deleteCommentByUser(Long userId, Long commentId) {
        Comment comment = commentRepository.findByIdAndAuthorId(commentId, userId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        comment.setStatus(CommentStatus.DELETED);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> getEventComments(Long eventId, Integer from, Integer size) {
        if (size <= 0 || from < 0) {
            throw new ValidationException("Invalid pagination parameters");
        }

        Pageable pageable = PageRequest.of(from / size, size);
        List<Comment> comments = commentRepository.findByEventIdAndStatus(
                eventId, CommentStatus.PUBLISHED, pageable);

        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto updateCommentByAdmin(Long commentId, UpdateCommentAdminRequest updateRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        if ("APPROVE".equals(updateRequest.getStatusAction())) {
            comment.setStatus(CommentStatus.PUBLISHED);
        } else if ("REJECT".equals(updateRequest.getStatusAction())) {
            comment.setStatus(CommentStatus.REJECTED);
        }

        comment.setModeratorComment(updateRequest.getModeratorComment());

        Comment updatedComment = commentRepository.save(comment);
        return CommentMapper.toCommentDto(updatedComment);
    }

    @Override
    public List<CommentDto> getCommentsByAdmin(
            List<Long> users,
            List<Long> events,
            List<CommentStatus> statuses,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size) {

        if (size <= 0 || from < 0) {
            throw new ValidationException("Invalid pagination parameters");
        }

        Pageable pageable = PageRequest.of(from / size, size);
        List<Comment> comments = commentRepository.findCommentsByAdmin(
                users, events, statuses, rangeStart, rangeEnd, pageable);

        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}