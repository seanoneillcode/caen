package com.lovely.games.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.Stage;

import static com.lovely.games.CaenMain.TILE_SIZE;

public class MoveVerb implements SceneVerb {

    float speed = TILE_SIZE * 3.0f;
    Vector2 pos, amount, total;
    boolean isDone;
    String actor;
    boolean isBlocking;
    boolean skip = false;

    public MoveVerb(Vector2 amount, String actor) {
        this.amount = amount;
        this.pos = new Vector2(speed, speed).scl(amount.cpy().nor());
        this.total = new Vector2();
        this.isDone = false;
        this.actor = actor;
        this.isBlocking = true;
    }

    public MoveVerb(Vector2 amount, String actor, boolean isBlocking) {
        this.amount = amount;
        this.pos = new Vector2(speed, speed).scl(amount.cpy().nor());
        this.total = new Vector2();
        this.isDone = false;
        this.actor = actor;
        this.isBlocking = isBlocking;
    }

    @Override
    public void start() {
        isDone = false;
        total = new Vector2();
    }

    @Override
    public void update(Stage stage) {
        if (skip) {
            Vector2 rest = amount.cpy().sub(total);
            stage.moveActor(actor, rest);
            isDone = true;
        } else {
            if (total.dst2(amount) < 16) {
                isDone = true;
            } else {
                Vector2 mov = pos.cpy().scl(Gdx.graphics.getDeltaTime());
                total.add(mov);
                stage.moveActor(actor, mov);
            }
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
