package com.techelevator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.techelevator.controller.CollectionController;
import com.techelevator.dao.CollectionDao;
import com.techelevator.dao.ComicDao;
import com.techelevator.dao.JdbcCollectionDao;
import com.techelevator.dao.JdbcComicDao;
import com.techelevator.model.Collection;
import com.techelevator.model.CollectionEntry;
import com.techelevator.model.Comic;
import com.techelevator.model.ComicData;
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

            rest.readComicAPI();
            List<Comic> comicsFromAPI = rest.getAllComics();

            for (Comic c: comicsFromAPI) {
                rest.readCharacterAPI(c.getId());
            }
            //add manual comics here

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
        }

            //Search comic title
//        List<Comic> comics = rest.searchComics("hulk");
//        for (Comic c : comics) {
//            System.out.println(c.getTitle());
//        }

            //Get public collections
//        List<Collection> collections = rest.getAllCollections();
//        for (Collection c : collections) {
//            System.out.println(c.getName());
//        }

            //Get comics in collection
//        List<Comic> comics2 = rest.getComicsInCollection(1);
//        for (Comic c : comics2) {
//            System.out.println(c.getId());
//        }

        //Get user's collections
//        List<Collection> collections = rest.getCollectionsForUser(1);
//        for (Collection c: collections) {
//            System.out.println(c.getName());
//        }

        //Get comic by id
//        Comic comic = rest.getComicById(1);
//        System.out.println(comic.getTitle());

        System.out.println("Done");
    }
}
