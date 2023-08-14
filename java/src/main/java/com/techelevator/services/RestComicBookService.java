package com.techelevator.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.techelevator.controller.ComicController;
import com.techelevator.dao.ComicDao;
import com.techelevator.dao.JdbcComicDao;
import com.techelevator.model.Comic;
import com.techelevator.model.ComicData;
import com.techelevator.model.ComicResponse;
import org.openqa.selenium.json.Json;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RestComicBookService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper mapper = new ObjectMapper();
//    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
//    JdbcComicDao jdbcComicDao = new JdbcComicDao(jdbcTemplate);
    private static final String API_BASE_URL = "http://gateway.marvel.com/v1/public/comics?ts=1&apikey=c30470c10899a5edb018d295b1cfa611&hash=bc4a36e9d13462b9323713e1aaec0750";

    public ComicData getComics(String title){
        ComicData result = restTemplate.getForObject(API_BASE_URL + "&titleStartsWith=" + title, ComicData.class);
        return result;
    }

    public List<Comic> getAllComics() {
        Comic[] responseEntity = restTemplate.getForObject(API_BASE_URL, Comic[].class);
        List<Comic> comics = Arrays.asList(responseEntity);
        return comics;
    }

    public void readComicAPI() {
        String jsonString = restTemplate.getForObject(API_BASE_URL, String.class);
        try {
            JsonNode tree = mapper.readTree(jsonString);
            JsonNode jsonNode = tree.at("/data/results");
            for (int i = 0; i < jsonNode.size(); i++) {
                Comic newComic = new Comic();
                JsonNode id = jsonNode.get(i).at("/id");
                newComic.setId(Integer.parseInt(id.asText()));
                JsonNode title = jsonNode.get(i).at("/title");
                newComic.setTitle(title.asText());
                JsonNode description = jsonNode.get(i).at("/description");
                newComic.setDescription(description.asText());
                JsonNode issueNumber = jsonNode.get(i).at("/issueNumber");
                newComic.setIssueNumber(Integer.parseInt(issueNumber.asText()));
                newComic.setDates("To be added");
//                JsonNode dates = jsonNode.get(i).at("/dates/type");
//                System.out.println(dates);
                //TODO: dates
                JsonNode series = jsonNode.get(i).at("/series/name");
                newComic.setSeries(series.asText());
                JsonNode images = jsonNode.get(i).at("/thumbnail/path");
                newComic.setImages(images.asText());
                restTemplate.postForObject("http://localhost:9000/comics", newComic, Comic.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
