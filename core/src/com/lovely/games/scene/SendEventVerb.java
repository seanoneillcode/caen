package com.lovely.games.scene;

import com.lovely.games.Stage;

public class SendEventVerb implements SceneVerb {

    String switchId;
    boolean isDone;

    public SendEventVerb(String switchId) {
        this.switchId = switchId;
        this.isDone = false;
    }

    @Override
    public void update(Stage stage) {
        if (!isDone) {
            isDone = true;
            stage.getTrunk().broadcast(switchId);
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

    @Override
    public void skip() {

    }
}
