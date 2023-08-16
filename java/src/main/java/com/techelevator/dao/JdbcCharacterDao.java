package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Char;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcCharacterDao implements CharacterDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCharacterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Char createCharacter(Char character) {
        Char newChar = null;
        String sql = "INSERT INTO character (character_id, name, description, character_image_url) " +
                "VALUES (?, ?, ?, ?) RETURNING character_serial;";
        try {
            int newCharacterId = jdbcTemplate.queryForObject(sql, int.class, character.getCharacterId(), character.getCharacterName(),
                    character.getCharacterDescription(), character.getCharacterImage());
            newChar = getCharacterById(newCharacterId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newChar;
    }

    @Override
    public Char getCharacterById(int characterId) {
        Char aChar = null;
        String sql = "SELECT * FROM character WHERE character_serial = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, characterId);
            if (result.next()) {
                aChar = mapRowToCharacter(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return aChar;
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
