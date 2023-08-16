package com.techelevator.controller;

import com.techelevator.dao.CollectionDao;
import com.techelevator.dao.ComicDao;
import com.techelevator.model.*;
import com.techelevator.services.RestComicBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class CollectionController {

    private final CollectionDao collectionDao;
    private final ComicDao comicDao;
    private final RestComicBookService service = new RestComicBookService();

    public CollectionController(CollectionDao collectionDao, ComicDao comicDao) {
        this.collectionDao = collectionDao;
        this.comicDao = comicDao;
    }


    @GetMapping("/search/{title}")
    public ComicData search(@PathVariable String title){
        return service.getComics(title);
    }


    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Comic> all(){
        return comicDao.getAllComics();
    }

    @RequestMapping(path = "/collections", method = RequestMethod.GET)
    public List<Collection> getCollections(@RequestParam int userId) {
        return collectionDao.getCollections(userId);
    }

    @RequestMapping(path = "/collections/{collectionId}", method = RequestMethod.GET)
    public List<Integer> getComicsInCollection(@PathVariable int collectionId) {
        List<Integer> comicIds = collectionDao.getComicsInCollection(collectionId);
        if (comicIds == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        } else {
            return comicIds;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/collections")
    public Collection create(@Valid @RequestBody Collection collection) {
        return collectionDao.createCollection(collection);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/collections/{collectionId}")
    public void addComicToCollection(@PathVariable int collectionId, int comicId) {
        collectionDao.addComicToCollection(collectionId, comicId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/collections/{collectionId}", method = RequestMethod.DELETE)
    public void deleteAllComicsFromCollection(@PathVariable int collectionId) {
        int rowsAffected = collectionDao.deleteAllComicsFromCollection(collectionId);
        if (rowsAffected == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
        }
    }


}
