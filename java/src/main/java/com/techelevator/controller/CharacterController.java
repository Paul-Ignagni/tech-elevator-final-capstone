package com.techelevator.controller;

import com.techelevator.dao.CharacterDao;
import com.techelevator.dao.ComicDao;
import com.techelevator.model.Char;
import com.techelevator.model.Collection;
import com.techelevator.model.Comic;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class CharacterController {

    private final CharacterDao characterDao;

    public CharacterController(CharacterDao characterDao) {
        this.characterDao = characterDao;
    }


    @RequestMapping(path = "/characters", method = RequestMethod.GET)
    public List<Char> getCharacters() {
        return characterDao.getAllCharacters();
    }

    @RequestMapping(path = "/characters/search/{name}", method = RequestMethod.GET)
    public List<Char> searchCharacters(@PathVariable String name){
        return characterDao.getCharactersBySearch(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/characters")
    public Char addCharacter(@Valid @RequestBody Char character) {
        return characterDao.createCharacter(character);
    }

    @RequestMapping(path = "/characters/{serial}", method = RequestMethod.GET)
    public Char getCharacterBySerial(@PathVariable int serial) {
        return characterDao.getCharacterBySerial(serial);
    }

    @RequestMapping(path = "/characters/characterId/{characterId}", method = RequestMethod.GET)
    public Char getCharacterById(@PathVariable int characterId) {
        return characterDao.getCharacterByCharacterId(characterId);
    }

}
