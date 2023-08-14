package com.techelevator.controller;

import com.techelevator.dao.ComicDao;
import com.techelevator.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class ComicController {

    private final ComicDao comicDao;
    @Autowired
    public ComicController(ComicDao comicDao) {
        this.comicDao = comicDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/comics")
    public Comic addComic(@Valid @RequestBody Comic comic) {
        return comicDao.createComic(comic);
    }
}
