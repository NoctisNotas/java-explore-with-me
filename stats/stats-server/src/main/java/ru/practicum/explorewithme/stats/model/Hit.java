package ru.practicum.explorewithme.stats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hit {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime created;
}
