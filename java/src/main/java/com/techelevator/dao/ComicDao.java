package com.techelevator.dao;

import com.techelevator.model.Comic;

import java.util.List;

public interface ComicDao {
    Comic createComic(Comic comic);

    List<Comic> getAllComics();

    List<Comic> getComicsBySearch(String search);

    Comic getComicById(int comicId);

    List<Comic> getComicsInCollection(int collectionId);

}
