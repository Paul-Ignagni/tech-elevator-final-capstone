package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Collection;
import com.techelevator.model.CollectionEntry;
import com.techelevator.model.Comic;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcCollectionDao implements CollectionDao {


    private final JdbcTemplate jdbcTemplate;

    public JdbcCollectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Collection> getCollections() {
        List<Collection> collections = new ArrayList<>();
        String sql = "SELECT * FROM collection WHERE isPublic = true";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Collection collection = mapRowToCollection(results);
                collections.add(collection);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return collections;
    }

    @Override
    public Collection getCollectionById(int collectionId) {
        Collection collection = null;
        String sql = "SELECT * FROM collection WHERE collection_id = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, collectionId);
            if (result.next()) {
                collection = mapRowToCollection(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return collection;
    }

    @Override
    public List<Collection> getCollectionsForUser(int userId) {
        List<Collection> collections = new ArrayList<>();
        String sql = "SELECT * FROM collection WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Collection collection = mapRowToCollection(results);
                collections.add(collection);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return collections;
    }

    @Override
    public List<String> getSeriesInCollection(int collectionId) {
        List<String> series = new ArrayList<>();
        String sql = "SELECT series FROM comic_info " +
                "JOIN collection_comic_info ON (comic_info.serial_number = collection_comic_info.serial_number) " +
                "WHERE collection_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            while (results.next()) {
                String string = results.getString("series");
                series.add(string);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return series;
    }

    @Override
    public List<String> getCharactersInCollection(int collectionId) {
        List<String> characters = new ArrayList<>();
        String sql = "SELECT DISTINCT name FROM character " +
                "JOIN character_comic_info ON (character.character_serial = character_comic_info.character_serial) " +
                "JOIN comic_info ON (comic_info.serial_number = character_comic_info.serial_number) " +
                "JOIN collection_comic_info ON (comic_info.serial_number = collection_comic_info.serial_number) " +
                "WHERE collection_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            while (results.next()) {
                String string = results.getString("name");
                characters.add(string);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return characters;
    }

    @Override
    public List<String> getCreatorsInCollection(int collectionId) {
        List<String> creators = new ArrayList<>();
        String sql = "SELECT DISTINCT name FROM creator " +
                "JOIN comic_info_creator ON (creator.creator_serial = comic_info_creator.creator_serial) " +
                "JOIN comic_info ON (comic_info.serial_number = comic_info_creator.serial_number) " +
                "JOIN collection_comic_info ON (comic_info.serial_number = collection_comic_info.serial_number) " +
                "WHERE collection_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            while (results.next()) {
                String string = results.getString("name");
                creators.add(string);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return creators;
    }

    @Override
    public int countTotalComics() {
        int totalComics = 0;
        String sql = "SELECT COUNT(serial_number) FROM collection_comic_info;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
           if (results.next()) {
                totalComics = results.getInt("count");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return totalComics;
    }

    @Override
    public int countTotalComicsInCollection(int collectionId) {
        int totalComics = 0;
        String sql = "SELECT COUNT(serial_number) FROM collection_comic_info WHERE collection_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            if (results.next()) {
                totalComics = results.getInt("count");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return totalComics;
    }

    @Override
    public Collection createCollection(Collection collection) {
        Collection newCollection = null;
        String sql = "INSERT INTO collection (user_id, collection_name, isPublic) VALUES (?, ?, ?) RETURNING collection_id;";
        try {
            int newCollectionId = jdbcTemplate.queryForObject(sql, int.class, collection.getUserId(),
                    collection.getName(), collection.isPublic());
            newCollection = getCollectionById(newCollectionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCollection;
    }

    @Override
    public void addComicToCollection(CollectionEntry entry) {
        String sql = "INSERT INTO collection_comic_info (collection_id, serial_number) VALUES (?, ?);";
        try {
            jdbcTemplate.update(sql, entry.getCollectionId(), entry.getComicId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public int deleteAllComicsFromCollection(int collectionId) {
        String deleteCollectionComicSql = "DELETE FROM collection_comic_info WHERE collection_id = ?";
        int numberOfDeletedComics = 0;
        try {
            numberOfDeletedComics = jdbcTemplate.update(deleteCollectionComicSql, collectionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfDeletedComics;
    }

    @Override
    public int deleteCollection(int collectionId) {
        String deleteCollectionSql = "DELETE FROM collection WHERE collection_id = ?";
        int rowsAffected = 0;
        try {
            rowsAffected = jdbcTemplate.update(deleteCollectionSql, collectionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rowsAffected;
    }

    @Override
    public int deleteComicFromCollection(int collectionId, int comicId) {
        int rowsAffected = 0;
        String deleteComicFromCollectionSql = "DELETE FROM collection_comic_book WHERE collection_id = ? AND comic_id = ?";
        try {
            rowsAffected = jdbcTemplate.update(deleteComicFromCollectionSql, collectionId, comicId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rowsAffected;
    }

    @Override
    public String getUsername(int collectionId) {
        String name = "";
        String sql = "SELECT username FROM users " +
                "JOIN collection ON (collection.user_id = users.user_id) " +
                "WHERE collection_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, collectionId);
            if (result.next()) {
                name = result.getString("username");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return name;
    }


    private Collection mapRowToCollection(SqlRowSet rs) {
        Collection collection = new Collection();
        collection.setId(rs.getInt("collection_id"));
        collection.setUserId(rs.getInt("user_id"));
        collection.setName(rs.getString("collection_name"));
        collection.setPublic(rs.getBoolean("isPublic"));
        return collection;
    }


}
