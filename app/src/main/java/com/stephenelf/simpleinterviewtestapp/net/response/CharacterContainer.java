package com.stephenelf.simpleinterviewtestapp.net.response;

import com.stephenelf.simpleinterviewtestapp.database.Character;

import java.util.List;

public class CharacterContainer extends Container {

    private List<Character> results;

    public List<Character> getResults() {
        return results;
    }

    public void setResults(List<Character> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "CharacterContainer{" +
                "results=" + results +
                '}';
    }
}
