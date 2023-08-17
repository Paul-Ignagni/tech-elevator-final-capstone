package com.techelevator.dao;

import com.techelevator.model.Char;

import java.util.List;

public interface CharacterDao {

    List<Char> getAllCharacters();

    List<Char> getCharactersBySearch(String name);

    Char createCharacter(Char character);

    Char getCharacterBySerial(int serial);

    Char getCharacterByCharacterId(int characterId);

    Char getCharacterByName(String name);

}
