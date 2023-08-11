package com.techelevator.model;

import java.util.Date;
import java.util.Map;

public class Comic {
    private int id;
    private String title;
    private String description;
    public int issueNumber;
    public Map<String, String> dates;
    private ComicImage [] images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public Map<String, String> getDates() {
        return dates;
    }

    public void setDates(Map<String, String> dates) {
        this.dates = dates;
    }

    public ComicImage[] getImages() {
        return images;
    }

    public void setImages(ComicImage[] images) {
        this.images = images;
    }
}
