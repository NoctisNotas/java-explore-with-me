package ru.practicum.explorewithme.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentRequest {

    @Size(min = 10, max = 2000)
    private String text;
}