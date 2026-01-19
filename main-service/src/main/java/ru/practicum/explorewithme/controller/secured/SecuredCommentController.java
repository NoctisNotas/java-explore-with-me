package ru.practicum.explorewithme.controller.secured;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.NewCommentDto;
import ru.practicum.explorewithme.dto.comment.UpdateCommentRequest;
import ru.practicum.explorewithme.service.CommentService;

@RestController
@RequiredArgsConstructor
public class SecuredCommentController {

    private final CommentService commentService;

    @PostMapping("/users/{userId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(
            @PathVariable Long userId,
            @Valid @RequestBody NewCommentDto newCommentDto) {

        return commentService.createComment(userId, newCommentDto);
    }

    @PatchMapping("/users/{userId}/comments/{commentId}")
    public CommentDto updateComment(
            @PathVariable Long userId,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest updateRequest) {

        return commentService.updateCommentByUser(userId, commentId, updateRequest);
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long userId,
            @PathVariable Long commentId) {

        commentService.deleteCommentByUser(userId, commentId);
    }
}