package com.lovely.games.dialog;

import com.badlogic.gdx.math.Vector2;

public class DialogSource {

    public Vector2 pos;
    public String id;
    public boolean done;

    public DialogSource(Vector2 pos, String id) {
        this.pos = pos;
        this.done = false;
        this.id = id;
    }
}
