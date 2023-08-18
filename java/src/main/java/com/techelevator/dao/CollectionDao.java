package com.techelevator.dao;

import com.techelevator.model.Collection;
import com.techelevator.model.CollectionEntry;
import com.techelevator.model.Comic;

import java.util.List;

public interface CollectionDao {

    List<Collection> getCollections();

    Collection getCollectionById(int collectionId);

    List<Collection> getCollectionsForUser(int userId);

    List<String> getSeriesInCollection(int collectionId);

    List<String> getCharactersInCollection(int collectionId);

    List<String> getCreatorsInCollection(int collectionId);

    int countTotalComics();

    Collection createCollection(Collection collection);

    int deleteAllComicsFromCollection(int collectionId);

    int deleteCollection(int collectionId);

    void addComicToCollection(CollectionEntry entry);

    int deleteComicFromCollection(int collectionId, int comicId);
}


