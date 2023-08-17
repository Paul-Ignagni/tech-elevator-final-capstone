package com.techelevator.controller;

import com.techelevator.dao.CreatorDao;
import com.techelevator.model.Char;
import com.techelevator.model.CollectionEntry;
import com.techelevator.model.Creator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class CreatorController {

    private final CreatorDao creatorDao;

    public CreatorController(CreatorDao creatorDao) {
        this.creatorDao = creatorDao;
    }

    @RequestMapping(path = "/creators", method = RequestMethod.GET)
    public List<Creator> getCreators() {
        return creatorDao.getAllCreators();
    }

    @RequestMapping(path = "/creators/search/{name}", method = RequestMethod.GET)
    public List<Creator> searchCreators(@PathVariable String name){
        return creatorDao.getCreatorsBySearch(name);
    }

    @RequestMapping(path = "/creators/database/{name}", method = RequestMethod.GET)
    public Creator getCreatorByName(@PathVariable String name){
        return creatorDao.getCreatorByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/creators")
    public Creator addCreator(@Valid @RequestBody Creator creator) {
        return creatorDao.addCreator(creator);
    }

    @RequestMapping(path = "/creators/{serial}", method = RequestMethod.GET)
    public Creator getCreatorBySerial(@PathVariable int serial) {
        return creatorDao.getCreatorBySerial(serial);
    }

    @RequestMapping(path = "/creators/creatorId/{creatorId}", method = RequestMethod.GET)
    public Creator getCreatorById(@PathVariable int creatorId) {
        return creatorDao.getCreatorByCreatorId(creatorId);
    }

}
