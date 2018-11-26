package com.lovely.games.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.lovely.games.Constants.TILE_SIZE;

public class Arrow {

    public boolean isRed;
    public boolean isArrow;
    public Vector2 pos;
    public Vector2 dir;
    public Color color;
    float speed;
    public boolean isDead;
    boolean isCollidingSelf;

    public Arrow(boolean isArrow, Vector2 pos, Vector2 dir, float speed, boolean isCollidingSelf, boolean isRed) {
        this.isArrow = isArrow;
        this.pos = pos;
        this.dir = dir;
        this.isDead = false;
        this.speed = speed;
        this.color = new Color(random(0.2f, 0.4f), random(0.7f, 1.0f) , random(0.5f, 0.8f), 1.0f);
        this.isCollidingSelf = isCollidingSelf;
        this.isRed = isRed;
        if (isRed) {
            this.color = new Color(random(0.9f, 1f), random(0.1f, 0.2f) , random(0.1f, 0.2f), 1.0f);
        }
    }

    public void update() {
        pos = pos.add(dir.cpy().scl(Gdx.graphics.getDeltaTime() * speed));
    }

    public Rectangle getRect() {
        float buffer = TILE_SIZE * 0.2f;
        float size = TILE_SIZE - buffer - buffer;
        return new Rectangle(pos.x + buffer, pos.y + buffer, size, size);
    }
}
