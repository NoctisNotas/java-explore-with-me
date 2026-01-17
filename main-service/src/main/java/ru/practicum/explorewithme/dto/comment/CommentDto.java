package ru.practicum.explorewithme.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.dto.user.UserShortDto;
import ru.practicum.explorewithme.util.DateTimePattern;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private UserShortDto author;
    private Long eventId;

    @JsonFormat(pattern = DateTimePattern.DATE_TIME)
    private LocalDateTime created;

    private String status;
    private String moderatorComment;
}