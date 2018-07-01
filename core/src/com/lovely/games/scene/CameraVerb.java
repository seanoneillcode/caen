package com.lovely.games.scene;

import com.badlogic.gdx.math.Vector2;
import com.lovely.games.Stage;

public class CameraVerb implements SceneVerb {

    boolean isDone;
    Vector2 target;

    public CameraVerb(Vector2 target) {
        this.isDone = false;
        this.target = target;
    }

    @Override
    public void update(Stage stage) {
        if (!isDone) {
            stage.moveCamera(target);
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
