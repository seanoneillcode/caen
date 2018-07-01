package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;

public class Explosion {
    private static final float EXPLOSION_TIME = 0.4f;
    private float timer;
    public Vector2 pos;
    Color color;
    private float alpha = 0;

    Explosion(Vector2 pos) {
        this.pos = pos;
        timer = 0;
        this.color = new Color(random(0.4f, 0.6f), random(0.8f, 1.0f) , random(0.7f, 1.0f), 1.0f);
    }

    public void update() {
        timer = timer + Gdx.graphics.getDeltaTime();
        float progress = Math.min(1f, timer/EXPLOSION_TIME);
        alpha = Interpolation.pow3.apply(progress);
    }

    public boolean isDone() {
        return timer > EXPLOSION_TIME;
    }

    public float getTimer() {
        return timer;
    }

    public float getAlpha() {
        return alpha;
    }
}
