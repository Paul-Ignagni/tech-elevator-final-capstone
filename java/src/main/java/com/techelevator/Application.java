package com.techelevator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.techelevator.controller.CollectionController;
import com.techelevator.dao.CollectionDao;
import com.techelevator.dao.ComicDao;
import com.techelevator.dao.JdbcCollectionDao;
import com.techelevator.dao.JdbcComicDao;
import com.techelevator.model.*;
import com.techelevator.services.RestComicBookService;
import org.openqa.selenium.json.CollectionCoercer;
import org.openqa.selenium.json.Json;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application {

    private CollectionDao collectionDao;
    private ComicDao comicDao;
    private final RestComicBookService rest = new RestComicBookService();
    private final CollectionController controller;



    public Application(JdbcTemplate jdbcTemplate) {
        collectionDao = new JdbcCollectionDao(jdbcTemplate);
        comicDao = new JdbcComicDao(jdbcTemplate);
        controller = new CollectionController(collectionDao, comicDao);

    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        Application application = new Application(jdbcTemplate);
        application.run();
    }

    private void run() {
        List<Comic> database = rest.getAllComics();
        if (database.size() == 0) {
            System.out.println("Creating database");

            List<ComicCreator> comicCreators = rest.readComicAPI();
            List<ComicCharacter> comicCharacters = rest.readComicAPI2();
            List<Comic> comicsFromAPI = rest.getAllComics();

            for (Comic c: comicsFromAPI) {
                rest.readCharacterAPI(c.getId());
                rest.readCreatorAPI(c.getId());
            }

            Collection privateCollection = new Collection();
            privateCollection.setUserId(1);
            privateCollection.setName("User Collection");
            privateCollection.setPublic(false);
            rest.createCollection(privateCollection);
            Collection publicCollection = new Collection();
            publicCollection.setUserId(2);
            publicCollection.setName("Admin Collection");
            publicCollection.setPublic(true);
            rest.createCollection(publicCollection);
            Collection publicCollection2 = new Collection();
            publicCollection2.setUserId(1);
            publicCollection2.setName("User Public Collection");
            publicCollection2.setPublic(true);
            rest.createCollection(publicCollection2);
            Collection publicCollection3 = new Collection();
            publicCollection3.setUserId(2);
            publicCollection3.setName("My Marvel Comics");
            publicCollection3.setPublic(true);
            rest.createCollection(publicCollection3);

            CollectionEntry entry1 = new CollectionEntry(1, 4);
            rest.addToCollection(entry1);
            CollectionEntry entry2 = new CollectionEntry(1, 8);
            rest.addToCollection(entry2);
            CollectionEntry entry3 = new CollectionEntry(1, 20);
            rest.addToCollection(entry3);
            CollectionEntry entry4 = new CollectionEntry(2, 3);
            rest.addToCollection(entry4);
            CollectionEntry entry5 = new CollectionEntry(2, 9);
            rest.addToCollection(entry5);
            CollectionEntry entry6 = new CollectionEntry(2, 11);
            rest.addToCollection(entry6);
            CollectionEntry entry7 = new CollectionEntry(4, 1);
            rest.addToCollection(entry7);

            for (ComicCreator c: comicCreators) {
                Creator creator = rest.getCreatorByNameDB(c.getCreatorName());
                Comic comic = rest.getComicByComicId(c.getComicId());
                ComicCreatorData data = new ComicCreatorData(comic.getSerial(), creator.getCreatorSerial());
                rest.addCreatorToComic(data.getComicSerial(), data);
            }

            for (ComicCharacter cc: comicCharacters) {
                Comic comic = rest.getComicByComicId(cc.getComicId());
                Char character = rest.getCharacterByNameDB(cc.getCharacterName());
                ComicCharacterData data = new ComicCharacterData(comic.getSerial(), character.getCharacterSerial());
                rest.addCharacterToComic(data.getComicSerial(), data);
            }

        }
        System.out.println("Done");
    }

}
