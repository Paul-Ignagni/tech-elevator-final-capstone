package com.techelevator.services;

import com.techelevator.model.ComicBook;
import org.springframework.web.client.RestTemplate;

public interface ComicBookService {
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_BASE_URL = "http://gateway.marvel.com/v1/public/comics?ts=1&apikey=c30470c10899a5edb018d295b1cfa611&hash=bc4a36e9d13462b9323713e1aaec0750";

}
