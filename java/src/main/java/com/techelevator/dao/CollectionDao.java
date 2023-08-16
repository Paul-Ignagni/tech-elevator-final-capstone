package com.techelevator.dao;

import com.techelevator.model.Collection;
import com.techelevator.model.CollectionEntry;
import com.techelevator.model.Comic;

import java.util.List;

public interface CollectionDao {

    List<Collection> getCollections();

    Collection getCollectionById(int collectionId);

    Collection createCollection(Collection collection);

    int deleteAllComicsFromCollection(int collectionId);

    int deleteCollection(int collectionId);

//    CollectionEntry getEntryById(int entryId);

//    void addComicToCollection(int collectionId, int comicId);
    void addComicToCollection(CollectionEntry entry);


    int deleteComicFromCollection(int collectionId, int comicId);
}


