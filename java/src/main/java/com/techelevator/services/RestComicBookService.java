package com.techelevator.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.*;
import com.techelevator.model.Collection;
import org.openqa.selenium.json.Json;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.parameters.P;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.Character;
import java.util.*;

public class RestComicBookService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String SERVER_BASE_URL = "http://localhost:9000";
    private static final String API_BASE_URL = "http://gateway.marvel.com/v1/public/comics?ts=1&apikey=c30470c10899a5edb018d295b1cfa611&hash=bc4a36e9d13462b9323713e1aaec0750";
    private static final String CHARACTER_BASE_URL1 = "https://gateway.marvel.com/v1/public/comics/";
    private static final String CHARACTER_BASE_URL2 = "/characters?ts=1&apikey=c30470c10899a5edb018d295b1cfa611&hash=bc4a36e9d13462b9323713e1aaec0750";
    private static final String CHARACTER_BASE_URL3 = "/creators?ts=1&apikey=c30470c10899a5edb018d295b1cfa611&hash=bc4a36e9d13462b9323713e1aaec0750";


    public List<Comic> getAllComics() {
        Comic[] responseEntity = restTemplate.getForObject(SERVER_BASE_URL + "/all", Comic[].class);
        List<Comic> comics = Arrays.asList(responseEntity);
        return comics;
    }

    public List<Comic> searchComics(String title) {
        Comic[] responseEntity = restTemplate.getForObject(SERVER_BASE_URL + "/search/" + title, Comic[].class);
        List<Comic> comics = Arrays.asList(responseEntity);
        return comics;
    }

    public Comic getComicById(int comicId) {
        Comic comic = restTemplate.getForObject(SERVER_BASE_URL + "/comic/" + comicId, Comic.class);
        return comic;
    }

    public Comic getComicByComicId(int realComicId) {
        Comic comic = restTemplate.getForObject(SERVER_BASE_URL + "/comic/comicId/" + realComicId, Comic.class);
        return comic;
    }

    public List<Collection> getAllCollections() {
        Collection[] responseEntity = restTemplate.getForObject(SERVER_BASE_URL + "/collections", Collection[].class);
        List<Collection> collections = Arrays.asList(responseEntity);
        return collections;
    }

    public List<Collection> getCollectionsForUser(int userId) {
        Collection[] responseEntity = restTemplate.getForObject(SERVER_BASE_URL + "/collections/myCollection/" + userId, Collection[].class);
        List<Collection> collections = Arrays.asList(responseEntity);
        return collections;
    }

    public List<Comic> getComicsInCollection(int collectionId) {
        Comic[] responseEntity = restTemplate.getForObject(SERVER_BASE_URL + "/collections/" + collectionId, Comic[].class);
        List<Comic> comics = Arrays.asList(responseEntity);
        return comics;
    }

    public List<Char> getAllCharacters() {
        Char[] responseEntity = restTemplate.getForObject(SERVER_BASE_URL + "/characters", Char[].class);
        List<Char> characters = Arrays.asList(responseEntity);
        return characters;
    }

    public List<Char> searchCharacters(String name) {
        Char[] responseEntity = restTemplate.getForObject(SERVER_BASE_URL + "/characters/search/" + name, Char[].class);
        List<Char> characters = Arrays.asList(responseEntity);
        return characters;
    }

    public List<Creator> getAllCreators() {
        Creator[] responseEntity = restTemplate.getForObject(SERVER_BASE_URL + "/creators", Creator[].class);
        List<Creator> creators = Arrays.asList(responseEntity);
        return creators;
    }

    public List<Creator> searchCreators(String name) {
        Creator[] responseEntity = restTemplate.getForObject(SERVER_BASE_URL + "/creators/search/" + name, Creator[].class);
        List<Creator> creators = Arrays.asList(responseEntity);
        return creators;
    }

    public Creator getCreatorByNameDB(String name) {
        Creator creator = restTemplate.getForObject(SERVER_BASE_URL + "/creators/database/" + name, Creator.class);
        return creator;
    }

    public Char getCharacterByNameDB(String name) {
        Char character = restTemplate.getForObject(SERVER_BASE_URL + "/characters/database/" + name, Char.class);
        return character;
    }

    public List<ComicCreator> readComicAPI() {
        String jsonString = restTemplate.getForObject(API_BASE_URL, String.class);
        List<ComicCreator> comicCreators = new ArrayList<>();
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
                JsonNode pageCount = jsonNode.get(i).at("/pageCount");
                newComic.setPageCount(Integer.parseInt(pageCount.asText()));
                JsonNode issueNumber = jsonNode.get(i).at("/issueNumber");
                newComic.setIssueNumber(Integer.parseInt(issueNumber.asText()));
                newComic.setDates("To be added");
                JsonNode series = jsonNode.get(i).at("/series/name");
                newComic.setSeries(series.asText());
                JsonNode images = jsonNode.get(i).at("/thumbnail/path");
                JsonNode type = jsonNode.get(i).at("/thumbnail/extension");
                String path = images.asText() + "." + type.asText();
                newComic.setImages(path);
                restTemplate.postForObject(SERVER_BASE_URL + "/comics", newComic, Comic.class);

                JsonNode authorNumber = jsonNode.get(i).at("/creators/available");
                if (authorNumber.asInt() > 0) {
                    JsonNode array = jsonNode.get(i).at("/creators/items");
                    for (int n = 0; n < authorNumber.asInt(); n++) {
                        JsonNode author = array.get(n).at("/name");
                        ComicCreator comicCreator = new ComicCreator(id.asInt(), author.asText());
                        comicCreators.add(comicCreator);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comicCreators;
    }

    public List<ComicCharacter> readComicAPI2() {
        String jsonString = restTemplate.getForObject(API_BASE_URL, String.class);
        List<ComicCharacter> comicCharacters = new ArrayList<>();
        try {
            JsonNode tree = mapper.readTree(jsonString);
            JsonNode jsonNode = tree.at("/data/results");
            for (int i = 0; i < jsonNode.size(); i++) {
                JsonNode id = jsonNode.get(i).at("/id");
                JsonNode characterNumber = jsonNode.get(i).at("/characters/available");
                if (characterNumber.asInt() > 0) {
                    JsonNode array2 = jsonNode.get(i).at("/characters/items");
                    for (int k = 0; k < characterNumber.asInt(); k++) {
                        JsonNode character = array2.get(k).at("/name");
                        ComicCharacter comicCharacter = new ComicCharacter(id.asInt(), character.asText());
                        comicCharacters.add(comicCharacter);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return comicCharacters;
    }

    public void readCharacterAPI(int comicId) {
        String jsonString = restTemplate.getForObject(CHARACTER_BASE_URL1 + comicId + CHARACTER_BASE_URL2, String.class);
        try {
            JsonNode tree = mapper.readTree(jsonString);
            JsonNode jsonNode = tree.at("/data/results");
            for(int i = 0; i < jsonNode.size(); i++) {
                JsonNode id = jsonNode.get(i).at("/id");
                int idInt = Integer.parseInt(id.asText());
                Char checkCharacter = restTemplate.getForObject(SERVER_BASE_URL + "/characters/characterId/" + idInt, Char.class);
                if (checkCharacter == null) {
                    Char character = new Char();
                    character.setCharacterId(idInt);
                    JsonNode name = jsonNode.get(i).at("/name");
                    character.setCharacterName(name.asText());
                    JsonNode description = jsonNode.get(i).at("/description");
                    character.setCharacterDescription(description.asText());
                    JsonNode image = jsonNode.get(i).at("/thumbnail/path");
                    JsonNode type = jsonNode.get(i).at("/thumbnail/extension");
                    String path = image.asText() + "." + type.asText();
                    character.setCharacterImage(path);
                    restTemplate.postForObject(SERVER_BASE_URL + "/characters", character, Char.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCreatorAPI(int comicId) {
        String jsonString = restTemplate.getForObject(CHARACTER_BASE_URL1 + comicId + CHARACTER_BASE_URL3, String.class);
        try {
            JsonNode tree = mapper.readTree(jsonString);
            JsonNode jsonNode = tree.at("/data/results");
            for(int i = 0; i < jsonNode.size(); i++) {
                JsonNode id = jsonNode.get(i).at("/id");
                int idInt = Integer.parseInt(id.asText());
                Creator checkCreator = restTemplate.getForObject(SERVER_BASE_URL + "/creators/creatorId/" + idInt, Creator.class);
                if (checkCreator == null) {
                    Creator creator = new Creator();
                    creator.setCreatorId(idInt);
                    JsonNode name = jsonNode.get(i).at("/fullName");
                    creator.setCreatorName(name.asText());
                    restTemplate.postForObject(SERVER_BASE_URL + "/creators", creator, Creator.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCollection(Collection newCollection) {
        try {
            restTemplate.postForObject(SERVER_BASE_URL + "/collections", newCollection, Collection.class);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }
    }

    public void addToCollection(CollectionEntry entry) {
        try {
            restTemplate.postForEntity( SERVER_BASE_URL + "/collections/" + entry.getCollectionId(), entry, CollectionEntry.class);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }
    }

    public void addCreatorToComic(int serialNumber, ComicCreatorData comicCreatorData) {
        try {
            restTemplate.postForEntity(SERVER_BASE_URL + "/creator/database/" + serialNumber, comicCreatorData, ComicCreatorData.class);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }
    }

    public void addCharacterToComic(int serialNumber, ComicCharacterData comicCharacterData) {
        try {
            restTemplate.postForEntity(SERVER_BASE_URL + "/character/database/" + serialNumber, comicCharacterData, ComicCharacterData.class);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }
    }

    public ComicResponse searchComicsByKeyword(String keyword) {
        String searchString = API_BASE_URL + "&titleStartsWith=" + keyword;
        ComicResponse responseEntity = restTemplate.getForObject(searchString, ComicResponse.class);
        return responseEntity;
    }

}
