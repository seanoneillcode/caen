package com.lovely.games.scene.verbs;

import com.badlogic.gdx.Gdx;
import com.lovely.games.Stage;
import com.lovely.games.scene.verbs.SceneVerb;

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
