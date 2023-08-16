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
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application {

    private CollectionDao collectionDao;
    private ComicDao comicDao;

    public Application(JdbcTemplate jdbcTemplate) {
        collectionDao = new JdbcCollectionDao(jdbcTemplate);
        comicDao = new JdbcComicDao(jdbcTemplate);

    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        Application application = new Application(jdbcTemplate);
        application.run();

    }

    private void run() {
        RestComicBookService rest = new RestComicBookService();
        rest.readComicAPI();

        Collection publicCollection = new Collection();
        publicCollection.setUserId(2);
        publicCollection.setName("Admin Collection");
        publicCollection.setPublic(false);
        rest.createCollection(publicCollection);

        Collection privateCollection = new Collection();
        privateCollection.setUserId(1);
        privateCollection.setName("User Collection");
        privateCollection.setPublic(true);
        rest.createCollection(privateCollection);

        CollectionController controller = new CollectionController(collectionDao, comicDao);
        controller.addComicToCollection(1, 4);
        controller.addComicToCollection(1, 8);
        controller.addComicToCollection(1, 20);
        System.out.println("done");
    }
}
