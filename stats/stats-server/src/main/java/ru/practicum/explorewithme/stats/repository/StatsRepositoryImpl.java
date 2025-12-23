package ru.practicum.explorewithme.stats.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.stats.dto.EndpointHitDto;
import ru.practicum.explorewithme.stats.dto.ViewStatsDto;
import ru.practicum.explorewithme.stats.mapper.ViewStatsRowMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsRepositoryImpl implements StatsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public void saveHit(EndpointHitDto hit) {
        jdbcTemplate.update(
                "INSERT INTO hits (app, uri, ip, created) VALUES (?, ?, ?, ?)",
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                Timestamp.valueOf(hit.getTimestamp())
        );
    }

    @Override
    public List<ViewStatsDto> getStats(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            boolean unique
    ) {
        String countField = unique ? "COUNT(DISTINCT ip)" : "COUNT(ip)";

        StringBuilder sql = new StringBuilder()
                .append("SELECT app, uri, ")
                .append(countField)
                .append(" AS hits ")
                .append("FROM hits ")
                .append("WHERE created BETWEEN :start AND :end ");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", Timestamp.valueOf(start));
        params.addValue("end", Timestamp.valueOf(end));

        if (uris != null && !uris.isEmpty()) {
            sql.append("AND uri IN (:uris) ");
            params.addValue("uris", uris);
        }

        sql.append("GROUP BY app, uri ").append("ORDER BY hits DESC");

        return namedJdbcTemplate.query(sql.toString(), params, new ViewStatsRowMapper());
    }
}