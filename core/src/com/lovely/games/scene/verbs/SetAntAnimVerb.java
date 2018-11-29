package com.lovely.games.scene.verbs;

import com.badlogic.gdx.Gdx;
import com.lovely.games.Stage;

public class SetAntAnimVerb implements SceneVerb {

    boolean isDone;
    private String anim;
    private float time;
    private boolean reset;
    private float timer;

    public SetAntAnimVerb(String anim, float time, boolean reset) {
        this.anim = anim;
        this.time = time;
        this.reset = reset;
        timer = 0;
    }

    @Override
    public void start() {
        isDone = false;
        timer = 0;
    }

    @Override
    public void update(Stage stage) {
        if (!isDone) {
            timer = timer + Gdx.graphics.getDeltaTime();
            stage.setAntAnim(anim, timer);
            if (timer > time) {
                isDone = true;
                if (reset) {
                    stage.setAntAnim("normal", 0);
                }
            }
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

    public void skip() {

    }
}
