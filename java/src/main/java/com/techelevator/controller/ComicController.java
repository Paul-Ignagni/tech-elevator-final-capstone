package com.techelevator.controller;

import com.techelevator.dao.CollectionDao;
import com.techelevator.model.ComicResponse;
import com.techelevator.services.RestComicBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ComicController {
    private final CollectionDao collectionDao;
    @Autowired
    public ComicController(CollectionDao collectionDao) {
        this.collectionDao = collectionDao;
    }
    @RequestMapping(path = "/search/{title}", method = RequestMethod.GET)
    public ComicResponse search(@PathVariable String title){
        RestComicBookService service = new RestComicBookService();
        return service.getComics(title);
    }
    @PostMapping("/collections/{collectionId}/addComic/{comicId}")
    public ResponseEntity<String> addComicToCollection(@PathVariable int collectionId, @PathVariable int comicId) {
        try {
            // Implement the logic to add the comic to the collection using CollectionDao
            boolean added = collectionDao.addComicToCollection(collectionId, comicId);

            if (added) {
                return ResponseEntity.ok("Comic added to collection successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to add comic to collection");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while adding comic to collection");
        }
    }
}
