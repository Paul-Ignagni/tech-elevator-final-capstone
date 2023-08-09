package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Collection;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcCollectionDao implements CollectionDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCollectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Collection> getCollections(int userId) {
        List<Collection> collections = new ArrayList<>();
        String sql = "SELECT collection_name FROM collection WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Collection collection = mapRowToCollection(results);
                collections.add(collection);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        }
        return collections;
    }

    @Override
    public Collection getCollectionById(int userId, int collectionId) {
        Collection collection = null;
        String sql = "SELECT collection_name FROM collection WHERE user_id = ? AND collection_id = ?";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId, collectionId);
            if (result.next()) {
                collection = mapRowToCollection(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        }
        return collection;
    }

    @Override
    public List<Integer> getComicsInCollection(int collectionId) {
        List<Integer> comicIds = new ArrayList<>();
        String sql = "SELECT comic_id FROM collection_comic_book WHERE collection_id = ?";
        int index = 0;
        int id = 0;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            while (results.next()) {
                id = results.getInt(index);
                index++;
                comicIds.add(id);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        }
        return comicIds;
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
