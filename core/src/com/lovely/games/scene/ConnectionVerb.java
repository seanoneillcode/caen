package com.lovely.games.scene;

import com.lovely.games.Stage;

public class ConnectionVerb implements SceneVerb {

    String connection;
    boolean isDone;

    public ConnectionVerb(String connection) {
        this.connection = connection;
        isDone = false;
    }

    @Override
    public void update(Stage stage) {
        stage.goToConnection(connection);
        isDone = true;
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
