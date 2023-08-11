package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class ComicData {
    private List<ComicData> results;

    public ComicData() {
        results = new ArrayList<>();
    }

    public List<ComicData> getResults() {
        return results;
    }

    public void setResults(List<ComicData> results) {
        this.results = results;
    }
}
