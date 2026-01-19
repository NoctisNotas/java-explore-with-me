package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.NewCommentDto;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.CommentStatus;

public class CommentMapper {

    public static Comment toComment(NewCommentDto newCommentDto) {
        Comment comment = new Comment();
        comment.setText(newCommentDto.getText());
        comment.setStatus(CommentStatus.PENDING);
        return comment;
    }

    public static CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());

        if (comment.getAuthor() != null) {
            dto.setAuthor(UserMapper.toUserShortDto(comment.getAuthor()));
        }

        if (comment.getEvent() != null) {
            dto.setEventId(comment.getEvent().getId());
        }

        dto.setCreated(comment.getCreated());
        dto.setStatus(comment.getStatus().name());
        dto.setModeratorComment(comment.getModeratorComment());
        return dto;
    }
}