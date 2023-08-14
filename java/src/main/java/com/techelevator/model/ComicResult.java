package com.techelevator.model;

import java.util.Date;

public class ComicResult {
    private String title;
    private int comicId;
    public int issueNumber;
    public Date releaseDate;
    private ComicImage [] images;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ComicImage[] getImages() {
        return images;
    }

    public void setImages(ComicImage[] images) {
        this.images = images;
    }
}
