package com.lovely.games.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.lovely.games.Constants.TILE_SIZE;

public class Block implements BlockLike {

    static final float TILE_SPEED = TILE_SIZE * 8.0f;

    public Vector2 pos;
    Vector2 dir;
    Vector2 startPos;
    boolean isMoving;
    float movementValue;
    public float animTimer;
    boolean isGround;
    public Color color;

    public Block(Vector2 pos) {
        this.startPos = pos.cpy();
        this.pos = pos;
        this.isMoving = false;
        this.dir = new Vector2();
        this.movementValue = 0;
        this.isGround = false;
        this.color = new Color(random(0.7f, 0.9f), random(0.0f, 0.1f), random(0.3f, 0.4f), 1.0f);
        animTimer = MathUtils.random(4.0f);
    }

    public void start() {
        this.pos = startPos.cpy();
        this.isGround = false;
        isMoving = false;
        movementValue = 0;
        this.dir = new Vector2();
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isGround() {
        return isGround;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos.cpy();
    }

    @Override
    public float getAnimTimer() {
        return animTimer;
    }

    @Override
    public void setGround(boolean isGround) {
        this.isGround = isGround;
    }

    public void move(Vector2 dir) {
        if (isGround) {
            return;
        }
//        if (dir.x != 0) {
//            dir.y = 0;
//        }
        this.dir = dir.cpy();
        isMoving = true;
        movementValue = TILE_SIZE / TILE_SPEED;
    }

    public void update() {
        animTimer += Gdx.graphics.getDeltaTime();
        if (isMoving) {
            float movementDelta = Gdx.graphics.getDeltaTime();
            movementValue = movementValue - movementDelta;
            if (movementValue < 0) {
                isMoving = false;
                movementDelta = movementDelta + movementValue;
            }
            Vector2 movement = dir.cpy().scl(movementDelta * TILE_SPEED);
            pos.add(movement);
        }
    }
}
