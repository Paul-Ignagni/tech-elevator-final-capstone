package com.techelevator.dao;

import com.techelevator.model.Collection;
import com.techelevator.model.CollectionEntry;

import java.util.List;

public interface CollectionDao {

    List<Collection> getCollections(int userId);

    Collection getCollectionById(int collectionId);

    List<Integer> getComicsInCollection(int collectionId);

    Collection createCollection(Collection collection);

    int deleteAllComicsFromCollection(int collectionId);

    int deleteCollection(int collectionId);

//    CollectionEntry getEntryById(int entryId);

    void addComicToCollection(int collectionId, int comicId);

    int deleteComicFromCollection(int collectionId, int comicId);
}


