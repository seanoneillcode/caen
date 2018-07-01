package com.lovely.games.scene;

import com.badlogic.gdx.Gdx;
import com.lovely.games.Stage;

public class WaitVerb implements SceneVerb {

    float timer;
    boolean isDone;
    float originalTimer;

    public WaitVerb(float timer) {
        this.timer = timer;
        this.isDone = false;
        this.originalTimer = timer;
    }

    @Override
    public void update(Stage stage) {
        if (!isDone) {
            timer = timer - Gdx.graphics.getDeltaTime();
        }
        if (timer < 0) {
            isDone = true;
        }
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public void start() {
        isDone = false;
        timer = originalTimer;
    }

    @Override
    public void skip() {
        timer = -1;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
