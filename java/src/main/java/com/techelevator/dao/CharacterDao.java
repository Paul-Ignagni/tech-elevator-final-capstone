package com.techelevator.dao;

import com.techelevator.model.Char;

public interface CharacterDao {
    Char createCharacter(Char character);

    Char getCharacterById(int characterId);
}
