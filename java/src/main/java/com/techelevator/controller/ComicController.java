package com.techelevator.controller;

import com.techelevator.dao.CollectionDao;
import com.techelevator.model.Collection;
import com.techelevator.model.ComicData;
import com.techelevator.model.ComicResponse;
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
public class ComicController {
    private final CollectionDao collectionDao;
    @Autowired
    public ComicController(CollectionDao collectionDao) {
        this.collectionDao = collectionDao;
    }
    @RequestMapping(path = "/search/{title}", method = RequestMethod.GET)
    public ComicData search(@PathVariable String title){
        RestComicBookService service = new RestComicBookService();
        return service.getComics(title);
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
    @PostMapping(path = "/collections/addCollection")
    public Collection create(@Valid @RequestBody Collection collection) {
        return collectionDao.createCollection(collection);
    }

    @PostMapping(path = "/collections/{collectionId}/addComic/{comicId}")
    public ResponseEntity<String> addComicToCollection(@PathVariable int collectionId, @PathVariable int comicId) {
        try {
            // Implement the logic to add the comic to the collection using CollectionDao
            int added = collectionDao.addComicToCollection(collectionId, comicId);

            if (added > 0) {
                return ResponseEntity.ok("Comic added to collection successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to add comic to collection");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while adding comic to collection");
        }
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
