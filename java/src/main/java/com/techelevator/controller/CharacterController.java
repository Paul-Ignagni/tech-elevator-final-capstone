package com.techelevator.controller;

import com.techelevator.dao.CharacterDao;
import com.techelevator.dao.ComicDao;
import com.techelevator.model.Char;
import com.techelevator.model.Comic;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class CharacterController {

    private final CharacterDao characterDao;

    public CharacterController(CharacterDao characterDao) {
        this.characterDao = characterDao;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/characters")
    public Char addCharacter(@Valid @RequestBody Char character) {
        return characterDao.createCharacter(character);
    }
}
