package com.lovely.games;

import com.badlogic.gdx.math.Vector2;

public class DialogSource {

    Vector2 pos;
    String id;
    boolean done;

    public DialogSource(Vector2 pos, String id) {
        this.pos = pos;
        this.done = false;
        this.id = id;
    }
}
