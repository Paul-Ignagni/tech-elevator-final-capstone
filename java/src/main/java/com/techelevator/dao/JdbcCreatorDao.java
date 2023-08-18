package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Char;
import com.techelevator.model.Comic;
import com.techelevator.model.Creator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCreatorDao implements CreatorDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcCreatorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Creator> getAllCreators() {
        List<Creator> creators = new ArrayList<>();
        String sql = "SELECT * FROM creator";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Creator creator = mapRowToCreator(results);
                creators.add(creator);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return creators;
    }

    @Override
    public List<Creator> getCreatorsBySearch(String name) {
        List<Creator> creators = new ArrayList<>();
        String sql = "SELECT * FROM creator WHERE name ILIKE ?;";
        String query = "%" + name + "%";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, query);
        while (results.next()) {
            creators.add(mapRowToCreator(results));
        }
        return creators;
    }

    @Override
    public Creator getCreatorByName(String name) {
        Creator creator = null;
        String sql = "SELECT * FROM creator WHERE name = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name);
            if (result.next()) {
                creator = mapRowToCreator(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return creator;
    }

    @Override
    public Creator addCreator(Creator creator) {
        Creator newCreator = null;
        String sql = "INSERT INTO creator (creator_id, name) VALUES (?, ?) RETURNING creator_serial;";
        try {
            int newCreatorSerial = jdbcTemplate.queryForObject(sql, int.class, creator.getCreatorId(),
                    creator.getCreatorName());
            newCreator = getCreatorBySerial(newCreatorSerial);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCreator;
    }

    @Override
    public Creator getCreatorBySerial(int serial) {
        Creator creator = null;
        String sql = "SELECT * FROM creator WHERE creator_serial = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, serial);
            if (result.next()) {
                creator = mapRowToCreator(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return creator;
    }

    @Override
    public Creator getCreatorByCreatorId(int creatorId) {
        Creator creator = null;
        String sql = "SELECT * FROM creator WHERE creator_id = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, creatorId);
            if (result.next()) {
                creator = mapRowToCreator(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return creator;
    }

    @Override
    public List<String> getComicsForCreator(int serial) {
        List<String> comics = new ArrayList<>();
        String sql = "SELECT title FROM comic_info " +
                "JOIN comic_info_creator ON (comic_info.serial_number = comic_info_creator.serial_number) " +
                "WHERE creator_serial = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, serial);
        while (results.next()) {
            String text = results.getString("title");
            comics.add(text);
        }
        return comics;
    }

    private Creator mapRowToCreator(SqlRowSet rs) {
        Creator creator = new Creator();
        creator.setCreatorSerial(rs.getInt("creator_serial"));
        creator.setCreatorId(rs.getInt("creator_id"));
        creator.setCreatorName(rs.getString("name"));
        return creator;
    }

}
