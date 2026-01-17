package ru.practicum.explorewithme.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventLocation {
    private Double lat;
    private Double lon;
}