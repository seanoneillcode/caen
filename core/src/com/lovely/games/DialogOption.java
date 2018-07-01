package com.lovely.games;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogOption implements DialogElement {

    private String owner;
    private Map<String, String> options;
    private int optionIndex;
    private boolean isDone;
    private String outcome;
    private String currentOption;
    private String mood;

    public DialogOption(String owner, Map<String, String> options, String mood) {
        this.owner = owner;
        this.options = options;
        optionIndex = 0;
        isDone = false;
        this.outcome = null;
        this.mood = mood;
    }

    @Override
    public void reset() {
        optionIndex = 0;
        isDone = false;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return isDone;
    }

    @Override
    public List<String> getLines() {
        List<String> lines = new ArrayList<>();
        int index = 0;
        for (String key : options.keySet()) {
            if (index != optionIndex) {
                lines.add("" + key);
            } else {
                lines.add("" + key);
                outcome = options.get(key);
                currentOption = key;
            }
            index++;
        }
        return lines;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void handleInput(Vector2 inputVector) {
        optionIndex = optionIndex - (int) inputVector.y;
        if (optionIndex < 0) {
            optionIndex = options.size() - 1;
        }
        if (optionIndex == options.size()) {
            optionIndex = 0;
        }
        if (inputVector.x == 1) {
            isDone = true;
        }
    }

    @Override
    public int getTotalLines() {
        return options.size();
    }

    @Override
    public String getChosenOption() {
        return outcome;
    }

    @Override
    public String getCurrentOption() {
        return currentOption;
    }

    @Override
    public String getMood() {
        return mood;
    }

    static class Builder {

        private String owner;
        private Map<String, String> options = new HashMap<>();
        private String mood = null;

        public Builder(String owner) {
            this.owner = owner;
        }

        public Builder opt(String dialog, String id) {
            options.put(dialog, id);
            return this;
        }

        public Builder mood(String mood) {
            this.mood = mood;
            return this;
        }

        public DialogOption build() {
            return new DialogOption(owner, options, mood);
        }
    }
}
