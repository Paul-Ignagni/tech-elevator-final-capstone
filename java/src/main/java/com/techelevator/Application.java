package com.techelevator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.techelevator.model.Comic;
import com.techelevator.model.ComicData;
import com.techelevator.services.RestComicBookService;
import org.openqa.selenium.json.Json;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(Application.class, args);

        RestComicBookService rest = new RestComicBookService();
        rest.readComicAPI();
    }

}
