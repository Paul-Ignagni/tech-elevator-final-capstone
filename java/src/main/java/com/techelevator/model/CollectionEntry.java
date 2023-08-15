package com.techelevator.model;

public class CollectionEntry {
    public int collectionId;
    public int comicId;

    public CollectionEntry() {
    }

    public CollectionEntry(int collectionId, int comicId) {
        this.collectionId = collectionId;
        this.comicId = comicId;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }
}
