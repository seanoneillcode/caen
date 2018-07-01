package com.lovely.games.scene;

import com.lovely.games.Stage;

public interface SceneVerb {

    void update(Stage stage);
    boolean isDone();
    boolean isBlocking();
    void start();
    void skip();
    default String getOutcome() {
        return null;
    }
}
