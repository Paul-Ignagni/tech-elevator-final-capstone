package com.techelevator.controller;

import com.techelevator.dao.ComicDao;
import com.techelevator.model.Collection;
import com.techelevator.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class ComicController {

    private final ComicDao comicDao;

    public ComicController(ComicDao comicDao) {
        this.comicDao = comicDao;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Comic> all(){
        return comicDao.getAllComics();
    }

    @RequestMapping(path = "/search/{title}", method = RequestMethod.GET)
    public List<Comic> search(@PathVariable String title){
        return comicDao.getComicsBySearch(title);
    }

    @RequestMapping(path = "/comic/{comicId}", method = RequestMethod.GET)
    public Comic getComicById(@PathVariable int comicId) {
        return comicDao.getComicById(comicId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/comics")
    public Comic addComic(@Valid @RequestBody Comic comic) {
        return comicDao.createComic(comic);
    }

}
