package com.lovely.games.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialogLine implements DialogElement {

    private static final float TIME_PER_CHAR = 20f;
    private static final int CHAR_PER_LINE = 32;

    float charTimer;
    int charIndex;

    String owner;
    int lineIndex;
    List<String> lines;
    boolean isDone;
    private boolean skipped;
    private String mood;

    public DialogLine(String owner, String line, String mood) {
        this.lines = parseLine(line);
        lineIndex = 0;
        this.owner = owner;
        charTimer = 0;
        charIndex = 0;
        isDone = false;
        skipped = false;
        this.mood = mood;
    }

    private List<String> parseLine(String line) {
        List<String> lines = new ArrayList<>();
        int charPerLineCount = 0;
        StringBuilder sb = new StringBuilder();
        List<String> words = Arrays.asList(line.split(" "));
        for (String word : words) {
            if (word.length() + charPerLineCount > CHAR_PER_LINE) {
                lines.add(sb.toString());
                charPerLineCount = 0;
                sb = new StringBuilder();
            }
            charPerLineCount += word.length();
            sb.append(word).append(" ");
        }
        lines.add(sb.toString());
        return lines;
    }

    public void update() {
        if (!isDone) {
            float delta = Gdx.graphics.getDeltaTime();
            charTimer = charTimer + (TIME_PER_CHAR * delta);
            charIndex = (int) charTimer;
            if (charIndex > lines.get(lineIndex).length()) {
                charIndex = 0;
                charTimer = 0;
                lineIndex += 1;
            }
            if (lineIndex == lines.size()) {
                lineIndex -= 1;
                isDone = true;
            }
            if (skipped) {
                isDone = true;
            }
        }
    }

    public String getMood() {
        return mood;
    }

    public List<String> getLines() {
        if (isDone) {
            return lines;
        }
        List<String> returnLines = new ArrayList<>();
        for (int i = 0 ; i < lineIndex; i++) {
            returnLines.add(lines.get(i));
        }
        returnLines.add(lines.get(lineIndex).substring(0,charIndex));
        return returnLines;
    }

    public void reset() {
        charIndex = 0;
        charTimer = 0;
        skipped = false;
        isDone = false;
    }

    public void handleInput(Vector2 inputVector) {
        skipped = true;
    }

    @Override
    public int getTotalLines() {
        return lines.size();
    }

    @Override
    public String getChosenOption() {
        return null;
    }

    @Override
    public String getCurrentOption() {
        return null;
    }


    public boolean isFinished() {
        return isDone;
    }

    public String getOwner() {
        return owner;
    }

    public static DialogLine line(String owner, String line) {
        return new DialogLine(owner, line, null);
    }

    public static DialogLine line(String owner, String line, String mood) {
        return new DialogLine(owner, line, mood);
    }

    public static DialogOption.Builder options(String owner) {
        return new DialogOption.Builder(owner);
    }
}
