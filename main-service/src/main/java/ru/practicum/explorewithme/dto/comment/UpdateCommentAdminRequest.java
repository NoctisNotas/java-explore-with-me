package ru.practicum.explorewithme.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentAdminRequest {

    private String statusAction;

    @Size(max = 500)
    private String moderatorComment;
}