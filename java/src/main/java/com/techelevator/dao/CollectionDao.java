package com.techelevator.dao;

import com.techelevator.model.Collection;

import java.util.List;

public interface CollectionDao {

    List<Collection> getCollections(int userId);

    Collection getCollectionById(int userId, int collectionId);

    List<Integer> getComicsInCollection(int collectionId);

}
