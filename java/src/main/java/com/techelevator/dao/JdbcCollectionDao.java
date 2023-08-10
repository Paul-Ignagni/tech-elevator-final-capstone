package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Collection;
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
    public List<Collection> getCollections(int userId) {
        List<Collection> collections = new ArrayList<>();
        String sql = "SELECT collection_id, collection_name, isPublic FROM collection WHERE user_id = ?";
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
    public Collection getCollectionById(int userId, int collectionId) {
        Collection collection = null;
        String sql = "SELECT collection_id, collection_name, isPublic FROM collection WHERE user_id = ? AND collection_id = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId, collectionId);
            if (result.next()) {
                collection = mapRowToCollection(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return collection;
    }

    @Override
    public List<Integer> getComicsInCollection(int collectionId) {
        List<Integer> comicIds = new ArrayList<>();
        String sql = "SELECT comic_id FROM collection_comic_book WHERE collection_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            while (results.next()) {
                comicIds.add(results.getInt("comic_id"));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return comicIds;
    }

    @Override
    public Collection createCollection(Collection collection) {
        Collection newCollection = null;
        String sql = "INSERT INTO collection (user_id, collection_name, isPublic) RETURNING collection_id;";
        try {
            int newCollectionId = jdbcTemplate.queryForObject(sql, int.class, collection.getUserId(),
                    collection.getName(), collection.isPublic());
            newCollection = getCollectionById(collection.getUserId(), newCollectionId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newCollection;
    }

    @Override
    public int addComicToCollection(int collectionId, int comicId) {
        int rowsAffected = 0;
        String addComicToCollectionSql = "INSERT INTO collection_comic_book (collection_id, comic_id) VALUES (?, ?)";
        try {
            rowsAffected = jdbcTemplate.update(addComicToCollectionSql, collectionId, comicId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rowsAffected;
    }

    @Override
    public int deleteAllComicsFromCollection(int collectionId) {
        String deleteCollectionComicSql = "DELETE FROM collection_comic_book WHERE collection_id = ?";
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

    private Collection mapRowToCollection(SqlRowSet rs) {
        Collection collection = new Collection();
        collection.setId(rs.getInt("collection_id"));
        collection.setUserId(rs.getInt("user_id"));
        collection.setName(rs.getString("collection_name"));
        collection.setPublic(rs.getBoolean("isPublic"));
        return collection;
    }

}
