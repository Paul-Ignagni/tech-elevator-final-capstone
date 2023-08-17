package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Char;
import com.techelevator.model.Collection;
import com.techelevator.model.Comic;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCharacterDao implements CharacterDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCharacterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Char> getAllCharacters() {
        List<Char> characters = new ArrayList<>();
        String sql = "SELECT * FROM character";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Char character = mapRowToCharacter(results);
                characters.add(character);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return characters;
    }

    @Override
    public List<Char> getCharactersBySearch(String name) {
        List<Char> characters = new ArrayList<>();
        String sql = "SELECT * FROM character WHERE name ILIKE ?;";
        String query = "%" + name + "%";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, query);
        while (results.next()) {
            characters.add(mapRowToCharacter(results));
        }
        return characters;
    }

    @Override
    public Char createCharacter(Char character) {
        Char newChar = null;
        String sql = "INSERT INTO character (character_id, name, description, character_image_url) " +
                "VALUES (?, ?, ?, ?) RETURNING character_serial;";
        try {
            int newCharacterSerial = jdbcTemplate.queryForObject(sql, int.class, character.getCharacterId(), character.getCharacterName(),
                    character.getCharacterDescription(), character.getCharacterImage());
            newChar = getCharacterBySerial(newCharacterSerial);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newChar;
    }

    @Override
    public Char getCharacterBySerial(int serial) {
        Char character = null;
        String sql = "SELECT * FROM character WHERE character_serial = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, serial);
            if (result.next()) {
                character = mapRowToCharacter(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return character;
    }

    @Override
    public Char getCharacterByCharacterId(int characterId) {
        Char character = null;
        String sql = "SELECT * FROM character WHERE character_id = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, characterId);
            if (result.next()) {
                character = mapRowToCharacter(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return character;
    }

    private Char mapRowToCharacter(SqlRowSet rs) {
        Char aChar = new Char();
        aChar.setCharacterSerial(rs.getInt("character_serial"));
        aChar.setCharacterId(rs.getInt("character_id"));
        aChar.setCharacterName(rs.getString("name"));
        aChar.setCharacterDescription(rs.getString("description"));
        aChar.setCharacterImage(rs.getString("character_image_url"));
        return aChar;
    }

}
