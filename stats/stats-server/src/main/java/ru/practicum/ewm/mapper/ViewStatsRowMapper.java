package ru.practicum.ewm.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.practicum.ewm.dto.ViewStatsDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewStatsRowMapper implements RowMapper<ViewStatsDto> {

    @Override
    public ViewStatsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ViewStatsDto dto = new ViewStatsDto();
        dto.setApp(rs.getString("app"));
        dto.setUri(rs.getString("uri"));
        dto.setHits(rs.getLong("hits"));
        return dto;
    }
}
