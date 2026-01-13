package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.event.EventFullDto;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.dto.event.NewEventDto;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.EventState;
import ru.practicum.explorewithme.model.User;

public class EventMapper {

    public static Event toEvent(NewEventDto newEventDto, User initiator) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setLat(newEventDto.getLocation().getLat());
        event.setLon(newEventDto.getLocation().getLon());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(newEventDto.getTitle());
        event.setInitiator(initiator);
        event.setState(EventState.PENDING);
        event.setConfirmedRequests(0);
        return event;
    }

    public static EventFullDto toEventFullDto(Event event) {
        if (event == null) {
            return null;
        }

        EventFullDto dto = new EventFullDto();
        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation() != null ? event.getAnnotation() : "");
        dto.setDescription(event.getDescription() != null ? event.getDescription() : "");
        dto.setTitle(event.getTitle() != null ? event.getTitle() : "");

        if (event.getCategory() != null) {
            dto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        } else {
            throw new IllegalArgumentException("Event category cannot be null for EventFullDto");
        }

        if (event.getConfirmedRequests() != null) {
            dto.setConfirmedRequests(event.getConfirmedRequests().longValue());
        } else {
            dto.setConfirmedRequests(0L);
        }

        dto.setCreatedOn(event.getCreatedOn());
        dto.setEventDate(event.getEventDate());

        if (event.getInitiator() != null) {
            dto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        } else {
            throw new IllegalArgumentException("Event initiator cannot be null for EventFullDto");
        }

        if (event.getLat() != null && event.getLon() != null) {
            dto.setLocation(new ru.practicum.explorewithme.dto.event.EventLocation(
                    event.getLat(), event.getLon()
            ));
        } else {
            throw new IllegalArgumentException("Event location cannot be null for EventFullDto");
        }

        dto.setPaid(event.getPaid() != null ? event.getPaid() : false);
        dto.setParticipantLimit(event.getParticipantLimit() != null ? event.getParticipantLimit() : 0);
        dto.setPublishedOn(event.getPublishedOn());
        dto.setRequestModeration(event.getRequestModeration() != null ? event.getRequestModeration() : true);

        if (event.getState() != null) {
            dto.setState(event.getState().name());
        } else {
            throw new IllegalArgumentException("Event state cannot be null for EventFullDto");
        }

        dto.setViews(event.getViews() != null ? event.getViews() : 0L);
        return dto;
    }

    public static EventShortDto toEventShortDto(Event event) {
        if (event == null) {
            return null;
        }

        EventShortDto dto = new EventShortDto();
        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation() != null ? event.getAnnotation() : "");
        dto.setTitle(event.getTitle() != null ? event.getTitle() : "");

        if (event.getCategory() != null) {
            dto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        } else {
            throw new IllegalArgumentException("Event category cannot be null for EventShortDto");
        }

        if (event.getConfirmedRequests() != null) {
            dto.setConfirmedRequests(event.getConfirmedRequests().longValue());
        } else {
            dto.setConfirmedRequests(0L);
        }

        dto.setEventDate(event.getEventDate());

        if (event.getInitiator() != null) {
            dto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        } else {
            throw new IllegalArgumentException("Event initiator cannot be null for EventShortDto");
        }

        dto.setPaid(event.getPaid() != null ? event.getPaid() : false);
        dto.setViews(event.getViews() != null ? event.getViews() : 0L);

        return dto;
    }
}