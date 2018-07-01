package com.lovely.games;


import com.badlogic.gdx.math.Vector2;

import static com.lovely.games.CaenMain.TILE_SIZE;

public class Connection {

    String name;
    String to;
    Vector2 pos;
    boolean active;
    Vector2 dir;

    public Connection(String name, String to, Vector2 pos, Vector2 dir) {
        this.name = name;
        this.to = to;
        this.pos = pos;
        this.active = false;
        this.dir = dir;
    }

    public void reset() {
        this.active = false;
    }

    public boolean contains(Vector2 other) {
        return other.x > pos.x && other.x < (pos.x + TILE_SIZE)
                && other.y > pos.y && other.y < (pos.y + TILE_SIZE);
    }
}
