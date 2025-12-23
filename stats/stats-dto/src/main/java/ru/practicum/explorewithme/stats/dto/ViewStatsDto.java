package ru.practicum.explorewithme.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewStatsDto {
    @NotBlank(message = "App cannot be blank")
    private String app;

    @NotBlank(message = "URI cannot be blank")
    private String uri;

    @NotNull(message = "Hits cannot be null")
    private Long hits;
}