package ru.practicum.explorewithme.stats.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.explorewithme.stats.dto.EndpointHitDto;
import ru.practicum.explorewithme.stats.dto.ViewStatsDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class StatsClient {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

        String encodedStart = URLEncoder.encode(start.format(FORMATTER), StandardCharsets.UTF_8);
        String encodedEnd = URLEncoder.encode(end.format(FORMATTER), StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(serverUrl + "/stats")
                .queryParam("start", encodedStart)
                .queryParam("end", encodedEnd)
                .queryParam("unique", unique);

        if (uris != null && !uris.isEmpty()) {
            builder.queryParam("uris", String.join(",", uris));
        }

        ResponseEntity<ViewStatsDto[]> response =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        null,
                        ViewStatsDto[].class
                );

        return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
    }
}