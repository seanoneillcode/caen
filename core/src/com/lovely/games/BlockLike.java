package com.lovely.games;

import com.badlogic.gdx.math.Vector2;

public interface BlockLike {

    void move(Vector2 dir);
    boolean isMoving();
    boolean isGround();
    Vector2 getPos();
    void setPos(Vector2 pos);
    float getAnimTimer();

    void setGround(boolean isGround);
}
