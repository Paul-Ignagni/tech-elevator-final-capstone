package com.techelevator.controller;

import com.techelevator.model.ComicResponse;
import com.techelevator.services.RestComicBookService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ComicController {
    @RequestMapping(path = "/search/{title}", method = RequestMethod.GET)
    public ComicResponse search(@PathVariable String title){
        RestComicBookService service = new RestComicBookService();
        return service.getComics(title);
    }

}
