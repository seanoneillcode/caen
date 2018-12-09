package com.lovely.games.scene.verbs;

import com.lovely.games.Stage;

public class CameraZoomVerb implements SceneVerb {

    boolean isDone;
    float target;

    public CameraZoomVerb(float target) {
        this.isDone = false;
        this.target = target;
    }

    @Override
    public void update(Stage stage) {
        if (!isDone) {
            stage.zoomCamera(target);
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
