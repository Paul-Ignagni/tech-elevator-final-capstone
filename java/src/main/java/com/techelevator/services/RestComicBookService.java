package com.techelevator.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Comic;
import com.techelevator.model.ComicData;
import com.techelevator.model.ComicResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RestComicBookService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper mapper = new ObjectMapper();
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



}
