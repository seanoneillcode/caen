package com.lovely.games.scene;

import com.badlogic.gdx.Gdx;
import com.lovely.games.Stage;

public class PosterVerb implements SceneVerb {

    boolean isDone;
    private float timer;
    private String stage;
    private float alpha;
    private String name;

    public PosterVerb(String name) {
        this.isDone = false;
        this.stage = "start";
        this.alpha = 0f;
        this.timer = 0f;
        this.name = name;
    }

    @Override
    public void update(Stage stage) {
        timer = timer + Gdx.graphics.getDeltaTime();
        if (this.stage.equals("start")) {
            this.alpha = this.alpha + Gdx.graphics.getDeltaTime();
            if (timer >= 1.0f) {
                this.stage = "middle";
            }
            stage.showPoster(alpha, name);
        }
        if (this.stage.equals("middle")) {
            alpha = 1.0f;
            if (timer >= 4.0f) {
                this.stage = "end";
            }
            stage.showPoster(alpha, name);
        }
        if (this.stage.equals("end")) {
            this.alpha = this.alpha - Gdx.graphics.getDeltaTime();
            stage.showPoster(alpha, name);
            if (timer >= 5.0f) {
                stage.showPoster(alpha, null);
                this.isDone = true;
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

    @Override
    public void start() {
        this.isDone = false;
        this.stage = "start";
        this.alpha = 0f;
        this.timer = 0f;
    }

    @Override
    public void skip() {
//        timer = 4.0f;
//        this.stage = "end";
    }
}
