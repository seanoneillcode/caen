package com.lovely.games.scene.verbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.Stage;

import static com.lovely.games.Constants.TILE_SIZE;

public class MoveVerb implements SceneVerb {

    private final Vector2 mov;
    float speed = TILE_SIZE * 3.0f;
    Vector2 amount;
    boolean isDone;
    String actor;
    boolean isBlocking;
    boolean skip = false;
    float time = 0;

    public MoveVerb(Vector2 amount, String actor) {
        this.amount = amount;
        this.time = amount.dst(new Vector2()) / speed;
        this.mov = new Vector2(speed, speed).scl(amount.cpy().nor());
        this.isDone = false;
        this.actor = actor;
        this.isBlocking = true;
    }

    @Override
    public void start() {
        isDone = false;
    }

    @Override
    public void update(Stage stage) {
        time = time - Gdx.graphics.getDeltaTime();
        if (time < 0) {
            stage.moveActor(actor, new Vector2());
            isDone = true;
        } else {
            stage.moveActor(actor, mov.cpy().scl(Gdx.graphics.getDeltaTime()));
        }
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean isBlocking() {
        return isBlocking;
    }

    public void skip() {
        skip = true;
    }
}
