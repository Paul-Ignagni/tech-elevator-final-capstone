package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Collection;
import com.techelevator.model.Comic;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcComicDao implements ComicDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcComicDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comic createComic(Comic comic) {
        Comic newComic = null;
        String sql = "INSERT INTO comic_info (comic_id, title, description, issueNumber, series, release_date, imageUrl)  " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING serial_number;";
        try {
            int newComicId = jdbcTemplate.queryForObject(sql, int.class, comic.getId(), comic.getTitle(),
                    comic.getDescription(), comic.getIssueNumber(), comic.getSeries(), comic.getDates(), comic.getImages());
            newComic = getComicById(newComicId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newComic;
    }


    @Override
    public Comic getComicById(int comicId) {
        Comic comic = null;
        String sql = "SELECT * FROM comic_info WHERE serial_number = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, comicId);
            if (result.next()) {
                comic = mapRowToComic(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return comic;
    }

    private Comic mapRowToComic(SqlRowSet rs) {
        Comic comic = new Comic();
        comic.setId(rs.getInt("serial_number"));
        comic.setTitle(rs.getString("title"));
        comic.setDescription(rs.getString("description"));
        comic.setIssueNumber(rs.getInt("issueNumber"));
        comic.setSeries(rs.getString("series"));
        comic.setDates("Not yet implemented");
        comic.setImages(rs.getString("imageUrl"));
        return comic;
    }















}
