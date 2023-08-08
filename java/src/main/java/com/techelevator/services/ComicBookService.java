package com.techelevator.services;

import com.techelevator.model.ComicBook;
import org.springframework.web.client.RestTemplate;

public interface ComicBookService {
    ComicBook getPic();

}
