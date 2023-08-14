package com.techelevator.dao;

import com.techelevator.model.Comic;

public interface ComicDao {
    Comic createComic(Comic comic);

    Comic getComicById(int comicId);
}
