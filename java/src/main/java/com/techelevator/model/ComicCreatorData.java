package com.techelevator.model;

public class ComicCreatorData {
    private int comicSerial;
    private int creatorSerial;

    public ComicCreatorData(int comicSerial, int creatorSerial) {
        this.comicSerial = comicSerial;
        this.creatorSerial = creatorSerial;
    }

    public ComicCreatorData() {
    }

    public int getComicSerial() {
        return comicSerial;
    }

    public void setComicSerial(int comicSerial) {
        this.comicSerial = comicSerial;
    }

    public int getCreatorSerial() {
        return creatorSerial;
    }

    public void setCreatorSerial(int creatorSerial) {
        this.creatorSerial = creatorSerial;
    }
}
