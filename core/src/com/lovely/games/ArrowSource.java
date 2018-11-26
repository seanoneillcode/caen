package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.lovely.games.Constants.ARROW_SPEED;
import static com.lovely.games.Constants.TILE_SIZE;

public class ArrowSource implements Switchable {

    private static final float STEP = 0.5f;
    boolean isRed;
    public Vector2 pos;
    Vector2 dir;
    float offset;
    float delay;
    float timer;
    boolean isActive;
    boolean originalIsActive;
    String switchId;
    boolean isRandom;
    float goal;
    float speed;
    public boolean isHidden;

    public ArrowSource(Vector2 pos, Vector2 dir, float offset, float delay, boolean isActive, String switchId, boolean isRandom, float speed, boolean isRed, boolean isHidden) {
        this.pos = pos;
        this.dir = dir;
        this.offset = offset;
        this.delay = delay;
        this.timer = 0;
        this.isActive = isActive;
        this.originalIsActive = isActive;
        this.switchId = switchId;
        this.speed = speed;
        this.isRandom = isRandom;
        this.isRed = isRed;
        this.isHidden = isHidden;
    }

    public void start() {
        this.timer = delay - offset;
        goal = delay;
        if (isRandom) {
            goal = MathUtils.random(STEP, STEP * delay);
        }
        this.isActive = originalIsActive;
    }

    public void update(CaenMain theFirstGate) {
        if (isActive) {
            timer = timer + Gdx.graphics.getDeltaTime();
            if (timer > goal) {
                if (isRandom) {
                    goal = MathUtils.random(STEP, STEP * delay);
                }
                timer = 0;
                Vector2 startPos = pos.cpy().add(dir.cpy().scl(TILE_SIZE));
                theFirstGate.addArrow(startPos, dir, ARROW_SPEED * speed, isRed);
            }
        }
    }

    public float getAnimTimer() {
        if (!isActive) {
            return 0;
        }
        float start = goal - 0.8f;
        if (timer > start) {
            return timer - start;
        } else {
            return 0;
        }
    }

    @Override
    public void handleMessage(String id) {
        if (this.switchId != null && this.switchId.equals(id)) {
            isActive = !isActive;
        }
    }
}
