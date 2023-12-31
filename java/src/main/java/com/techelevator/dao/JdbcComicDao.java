package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcComicDao implements ComicDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcComicDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comic createComic(Comic comic) {
        Comic newComic = null;
        String sql = "INSERT INTO comic_info (comic_id, title, description, issueNumber, series, release_date, pageCount, imageUrl)  " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING serial_number;";
        try {
            int newComicId = jdbcTemplate.queryForObject(sql, int.class, comic.getId(), comic.getTitle(),
                    comic.getDescription(), comic.getIssueNumber(), comic.getSeries(), comic.getDates(), comic.getPageCount(), comic.getImages());
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

    @Override
    public Comic getComicByComicId(int realComicId) {
        Comic comic = null;
        String sql = "SELECT * FROM comic_info WHERE comic_id = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, realComicId);
            if (result.next()) {
                comic = mapRowToComic(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return comic;
    }

    @Override
    public List<Comic> getAllComics() {
        List<Comic> output = new ArrayList<>();
        String sql = "SELECT * FROM comic_info";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            output.add(mapRowToComic(results));
        }
        return output;
    }

    @Override
    public List<Comic> getComicsBySearch(String title) {
        List<Comic> comics = new ArrayList<>();
        String sql = "SELECT * FROM comic_info WHERE title ILIKE ?;";
        String query = "%" + title + "%";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, query);
        while (results.next()) {
            comics.add(mapRowToComic(results));
        }
        return comics;
    }

    @Override
    public List<Comic> getComicsInCollection(int collectionId) {
        List<Comic> comics = new ArrayList<>();
        String sql = "SELECT comic_info.serial_number, comic_info.comic_id, title, description, issueNumber, series, release_date, pageCount, imageUrl " +
                "FROM comic_info JOIN collection_comic_info ON comic_info.serial_number = collection_comic_info.serial_number " +
                "WHERE collection_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            while (results.next()) {
                comics.add(mapRowToComic(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return comics;
    }

    @Override
    public List<String> getCreatorsForComic(int serial) {
        List<String> authors = new ArrayList<>();
        String sql = "SELECT name FROM creator " +
                "JOIN comic_info_creator ON (creator.creator_serial = comic_info_creator.creator_serial) " +
                "JOIN comic_info ON (comic_info.serial_number = comic_info_creator.serial_number) " +
                "WHERE comic_info.serial_number = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, serial);
        while (results.next()) {
            String text = results.getString("name");
            authors.add(text);
        }
        return authors;
    }

    @Override
    public List<String> getCharactersForComic(int serial) {
        List<String> characters = new ArrayList<>();
        String sql = "SELECT name FROM character " +
                "JOIN character_comic_info ON (character.character_serial = character_comic_info.character_serial) " +
                "JOIN comic_info ON (comic_info.serial_number = character_comic_info.serial_number) " +
                "WHERE comic_info.serial_number =?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, serial);
        while (results.next()) {
            String text = results.getString("name");
            characters.add(text);
        }
        return characters;
    }

    @Override
    public void addCreatorToComic(int serialNumber, ComicCreatorData comicCreatorData) {
        String sql = "INSERT INTO comic_info_creator (serial_number, creator_serial) VALUES (?, ?);";
        try {
            jdbcTemplate.update(sql, serialNumber, comicCreatorData.getCreatorSerial());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void addCharacterToComic(int serialNumber, ComicCharacterData comicCharacterData) {
        String sql = "INSERT INTO character_comic_info (serial_number, character_serial) VALUES (?, ?);";
        try {
            jdbcTemplate.update(sql, serialNumber, comicCharacterData.getCharacterSerial());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Comic mapRowToComic(SqlRowSet rs) {
        Comic comic = new Comic();
        comic.setSerial(rs.getInt("serial_number"));
        comic.setId(rs.getInt("comic_id"));
        comic.setTitle(rs.getString("title"));
        comic.setDescription(rs.getString("description"));
        comic.setIssueNumber(rs.getInt("issueNumber"));
        comic.setSeries(rs.getString("series"));
        comic.setDates("Not yet implemented");
        comic.setPageCount(rs.getInt("pageCount"));
        comic.setImages(rs.getString("imageUrl"));
        return comic;
    }















}
