package com.techelevator.services;

import com.techelevator.BasicLogger.BasicLogger;
import com.techelevator.model.ComicBook;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
@Component
public class RestComicBookService implements ComicBookService {
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_BASE_URL = "http://gateway.marvel.com/v1/public/comics?ts=1&apikey=c30470c10899a5edb018d295b1cfa611&hash=bc4a36e9d13462b9323713e1aaec0750";

    @Override
    public ComicBook getPic() {
        ComicBook comicBook = null;
        try{
            comicBook = restTemplate.getForObject(API_BASE_URL, ComicBook.class);
        }catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return comicBook;
    }


}
