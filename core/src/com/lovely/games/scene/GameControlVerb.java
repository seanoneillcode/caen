package com.lovely.games.scene;

import com.lovely.games.Stage;

public class GameControlVerb implements SceneVerb {

    boolean isDone;
    String state;

    public GameControlVerb(String state) {
        this.isDone = false;
        this.state = state;
    }

    @Override
    public void update(Stage stage) {
        if (!isDone) {
            stage.gotoState(state);
            isDone = true;
        }
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public void start() {
        isDone = false;
    }

    public void skip() {

    }
}
