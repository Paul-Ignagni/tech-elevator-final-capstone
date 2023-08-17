package com.techelevator.dao;

import com.techelevator.model.Creator;

import java.util.List;

public interface CreatorDao {

    List<Creator> getAllCreators();

    List<Creator> getCreatorsBySearch(String name);

    Creator addCreator(Creator creator);

    Creator getCreatorBySerial(int serial);

    Creator getCreatorByCreatorId(int creatorId);
}
