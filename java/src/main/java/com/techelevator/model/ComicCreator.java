package com.techelevator.model;

public class ComicCreator {
    private int comicId;
    private String creatorName;

    public ComicCreator(int comicId, String creatorName) {
        this.comicId = comicId;
        this.creatorName = creatorName;
    }

    public ComicCreator() {
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

}
