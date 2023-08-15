package com.techelevator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.techelevator.dao.CollectionDao;
import com.techelevator.dao.JdbcCollectionDao;
import com.techelevator.model.Collection;
import com.techelevator.model.CollectionEntry;
import com.techelevator.model.Comic;
import com.techelevator.model.ComicData;
import com.techelevator.services.RestComicBookService;
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

    public Application(JdbcTemplate jdbcTemplate) {
        collectionDao = new JdbcCollectionDao(jdbcTemplate);
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

        CollectionEntry entry1 = new CollectionEntry(1, 3);
        rest.addToCollection(entry1);
        CollectionEntry entry2 = new CollectionEntry(1,9);
        rest.addToCollection(entry2);
        CollectionEntry entry3 = new CollectionEntry(1,20);
        rest.addToCollection(entry3);
        System.out.println("done");
    }
}
