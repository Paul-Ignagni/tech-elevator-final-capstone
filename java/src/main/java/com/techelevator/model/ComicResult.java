package com.techelevator.model;

public class ComicResult {
    private String title;

    private int issueNumber;

    private int pageCount;

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

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public ComicImage[] getImages() {
        return images;
    }

    public void setImages(ComicImage[] images) {
        this.images = images;
    }
}
