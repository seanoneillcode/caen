package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.lovely.games.CaenMain.TILE_SIZE;

class Block implements BlockLike {

    static final float TILE_SPEED = TILE_SIZE * 4.0f;

    Vector2 pos;
    Vector2 dir;
    Vector2 startPos;
    boolean isMoving;
    float movementValue;
    float animTimer;
    boolean isGround;
    Color color;

    Block(Vector2 pos) {
        this.startPos = pos.cpy();
        this.pos = pos;
        this.isMoving = false;
        this.dir = new Vector2();
        this.movementValue = 0;
        this.isGround = false;
        this.color = new Color(random(0.8f, 1.0f), random(0.1f, 0.2f), random(0.1f, 0.2f), 1.0f);
        animTimer = MathUtils.random(4.0f);
    }

    void start() {
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
