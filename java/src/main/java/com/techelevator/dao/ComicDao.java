package com.techelevator.dao;

import com.techelevator.model.Comic;

import java.util.List;

public interface ComicDao {
    Comic createComic(Comic comic);

    List<Comic> getAllComics();

    Comic getComicById(int comicId);
}
