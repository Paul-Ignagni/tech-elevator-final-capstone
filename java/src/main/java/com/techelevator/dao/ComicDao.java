package com.techelevator.dao;

import com.techelevator.model.Comic;
import com.techelevator.model.ComicCharacterData;
import com.techelevator.model.ComicCreator;
import com.techelevator.model.ComicCreatorData;

import java.util.List;

public interface ComicDao {
    Comic createComic(Comic comic);

    List<Comic> getAllComics();

    List<Comic> getComicsBySearch(String search);

    Comic getComicById(int comicId);

    Comic getComicByComicId(int realComicId);

    List<Comic> getComicsInCollection(int collectionId);

    List<String> getCreatorsForComic(int serial);

    List<String> getCharactersForComic(int serial);

    void addCreatorToComic(int serialNumber, ComicCreatorData comicCreatorData);


    void addCharacterToComic(int serialNumber, ComicCharacterData comicCharacterData);


}
