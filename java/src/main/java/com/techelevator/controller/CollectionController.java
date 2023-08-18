package com.techelevator.controller;

import com.techelevator.dao.CollectionDao;
import com.techelevator.dao.ComicDao;
import com.techelevator.model.*;
import com.techelevator.services.RestComicBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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


    @RequestMapping(path = "/collections", method = RequestMethod.GET)
    public List<Collection> getCollections() {
        return collectionDao.getCollections();
    }

    @RequestMapping(path = "/collections/myCollection/{userId}", method = RequestMethod.GET)
    public List<Collection> getMyCollections(@PathVariable int userId) {
        return collectionDao.getCollectionsForUser(userId);
    }

    @RequestMapping(path = "/collections/{collectionId}", method = RequestMethod.GET)
    public List<Comic> getComicsInCollection(@PathVariable int collectionId) {
        List<Comic> comics = comicDao.getComicsInCollection(collectionId);
        return comics;
    }

    @RequestMapping(path = "/collections/{collectionId}/series", method = RequestMethod.GET)
    public List<String> getSeriesInCollection(@PathVariable int collectionId) {
        List<String> series = collectionDao.getSeriesInCollection(collectionId);
        return series;
    }

    @RequestMapping(path = "/collections/{collectionId}/characters", method = RequestMethod.GET)
    public List<String> getCharactersInCollection(@PathVariable int collectionId) {
        List<String> characters = collectionDao.getCharactersInCollection(collectionId);
        return characters;
    }

    @RequestMapping(path = "/collections/{collectionId}/creators", method = RequestMethod.GET)
    public List<String> getCreatorsInCollection(@PathVariable int collectionId) {
        List<String> creators = collectionDao.getCreatorsInCollection(collectionId);
        return creators;
    }

    @RequestMapping(path = "/collections/totalcomics", method = RequestMethod.GET)
    public int countTotalComics() {
        int totalComics = collectionDao.countTotalComics();
        return totalComics;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/collections")
    public Collection create(@Valid @RequestBody Collection collection) {
        return collectionDao.createCollection(collection);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/collections/{collectionId}")
    public ResponseEntity<String> addComicToCollection(@PathVariable int collectionId,
                                                       @RequestBody CollectionEntry entry,
                                                       @AuthenticationPrincipal User user) {
        Collection collection = collectionDao.getCollectionById(collectionId);
        if (collection != null) {
            String userGrade = ((User) user).getGrade();

            List<Comic> comicsInCollection = comicDao.getComicsInCollection(collectionId);

            if ("standard".equals(userGrade) && comicsInCollection.size() >= 100) {
                return ResponseEntity
                        .badRequest()
                        .body("Oops! Standard users can only add up to 100 comics to their collection - maybe you should go premium!");
            }

            collectionDao.addComicToCollection(entry);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection not found");
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
