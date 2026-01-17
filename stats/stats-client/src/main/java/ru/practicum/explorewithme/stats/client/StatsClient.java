package ru.practicum.explorewithme.stats.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.explorewithme.stats.dto.DateTimePattern;
import ru.practicum.explorewithme.stats.dto.EndpointHitDto;
import ru.practicum.explorewithme.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class StatsClient {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(DateTimePattern.DATE_TIME);

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public StatsClient(RestTemplate restTemplate, String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    public void saveHit(EndpointHitDto hit) {
        restTemplate.postForLocation(
                serverUrl + "/hit",
                new HttpEntity<>(hit)
        );
    }

    public List<ViewStatsDto> getStats(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            boolean unique
    ) {
        String startStr = start.format(FORMATTER);
        String endStr = end.format(FORMATTER);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(serverUrl + "/stats")
                .queryParam("start", startStr)
                .queryParam("end", endStr)
                .queryParam("unique", unique);

        if (uris != null && !uris.isEmpty()) {
            for (String uri : uris) {
                builder.queryParam("uris", uri);
            }
        }

        ResponseEntity<ViewStatsDto[]> response =
                restTemplate.exchange(
                        builder.build().toUri(),
                        HttpMethod.GET,
                        null,
                        ViewStatsDto[].class
                );

        return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
    }
}