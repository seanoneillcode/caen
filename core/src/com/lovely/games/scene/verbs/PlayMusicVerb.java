package com.lovely.games.scene.verbs;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.Stage;

import static com.lovely.games.Constants.RANDOM_SOUND_ID_RANGE;


public class PlayMusicVerb implements SceneVerb {

    boolean isDone;
    String name;
    int id;

    public PlayMusicVerb(String name) {
        this.isDone = false;
        this.name = name;
        this.id = MathUtils.random(RANDOM_SOUND_ID_RANGE);
    }

    @Override
    public void update(Stage stage) {
        if (!isDone) {
            isDone = true;
            stage.playMusic(name);
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

    @Override
    public String getOutcome() {
        return null;
    }
}
