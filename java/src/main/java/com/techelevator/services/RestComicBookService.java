package com.techelevator.services;

import com.techelevator.model.ComicResponse;
import org.springframework.web.client.RestTemplate;

public class RestComicBookService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_BASE_URL = "http://gateway.marvel.com/v1/public/comics?ts=1&apikey=c30470c10899a5edb018d295b1cfa611&hash=bc4a36e9d13462b9323713e1aaec0750";

    public ComicResponse getComics(String title){
        ComicResponse result = restTemplate.getForObject(API_BASE_URL + "&titleStartsWith=" + title, ComicResponse.class);
        return result;
    }


}
