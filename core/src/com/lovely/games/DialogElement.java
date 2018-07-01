package com.lovely.games;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public interface DialogElement {

    void reset();
    void update();
    boolean isFinished();
    List<String> getLines();
    String getOwner();
    void handleInput(Vector2 inputVector);
    int getTotalLines();
    String getChosenOption();
    String getCurrentOption();
    String getMood();
}
