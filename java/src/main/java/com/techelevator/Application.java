package com.techelevator;

import com.techelevator.model.Comic;
import com.techelevator.model.ComicData;
import com.techelevator.services.RestComicBookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        RestComicBookService rest = new RestComicBookService();
        List<Comic> data = rest.getAllComics();
        for (Comic comic : data) {
            System.out.println(comic);
        }
    }

}
