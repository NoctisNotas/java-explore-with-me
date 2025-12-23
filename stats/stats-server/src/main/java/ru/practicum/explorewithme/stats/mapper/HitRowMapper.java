package ru.practicum.explorewithme.stats.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.practicum.ewm.model.Hit;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HitRowMapper implements RowMapper<Hit> {

    @Override
    public Hit mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Hit(
                rs.getLong("id"),
                rs.getString("app"),
                rs.getString("uri"),
                rs.getString("ip"),
                rs.getTimestamp("created").toLocalDateTime()
        );
    }
}
