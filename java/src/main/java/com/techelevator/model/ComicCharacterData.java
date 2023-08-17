package com.techelevator.model;

public class ComicCharacterData {
    private int comicSerial;
    private int characterSerial;

    public ComicCharacterData(int comicSerial, int characterSerial) {
        this.comicSerial = comicSerial;
        this.characterSerial = characterSerial;
    }

    public ComicCharacterData() {
    }

    public int getComicSerial() {
        return comicSerial;
    }

    public void setComicSerial(int comicSerial) {
        this.comicSerial = comicSerial;
    }

    public int getCharacterSerial() {
        return characterSerial;
    }

    public void setCharacterSerial(int characterSerial) {
        this.characterSerial = characterSerial;
    }
}
