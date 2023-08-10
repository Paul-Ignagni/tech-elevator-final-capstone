package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Collection;
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
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Collection collection = mapRowToCollection(results);
            collections.add(collection);
        }
        return collections;
    }

    @Override
    public Collection getCollectionById(int userId, int collectionId) {
        String sql = "SELECT collection_id, collection_name, isPublic FROM collection WHERE user_id = ? AND collection_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId, collectionId);
        if (result.next()) {
            return mapRowToCollection(result);
        } else {
            return null; // Collection not found
        }
    }

    @Override
    public List<Integer> getComicsInCollection(int collectionId) {
        List<Integer> comicIds = new ArrayList<>();
        String sql = "SELECT comic_id FROM collection_comic_book WHERE collection_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
        while (results.next()) {
            comicIds.add(results.getInt("comic_id"));
        }
        return comicIds;
    }
    @Override
    public boolean addComicToCollection(int collectionId, int comicId) {
        String addComicToCollectionSql = "INSERT INTO collection_comic_book (collection_id, comic_id) VALUES (?, ?)";
        int rowsAffected = jdbcTemplate.update(addComicToCollectionSql, collectionId, comicId);

        return rowsAffected > 0;
    }
    @Override
    public boolean deleteCollection(int userId, int collectionId) {
        String deleteCollectionSql = "DELETE FROM collection WHERE user_id = ? AND collection_id = ?";
        int rowsAffected = jdbcTemplate.update(deleteCollectionSql, userId, collectionId);

        String deleteCollectionComicSql = "DELETE FROM collection_comic_book WHERE collection_id = ?";
        jdbcTemplate.update(deleteCollectionComicSql, collectionId);

        return rowsAffected > 0;
    }
    @Override
    public boolean deleteComicFromCollection(int collectionId, int comicId) {
        String deleteComicFromCollectionSql = "DELETE FROM collection_comic_book WHERE collection_id = ? AND comic_id = ?";
        int rowsAffected = jdbcTemplate.update(deleteComicFromCollectionSql, collectionId, comicId);

        return rowsAffected > 0;
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
