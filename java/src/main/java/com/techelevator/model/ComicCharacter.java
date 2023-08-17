package com.techelevator.model;

public class ComicCharacter {
    private int comicId;
    private String characterName;

    public ComicCharacter(int comicId, String characterName) {
        this.comicId = comicId;
        this.characterName = characterName;
    }

    public ComicCharacter() {
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
