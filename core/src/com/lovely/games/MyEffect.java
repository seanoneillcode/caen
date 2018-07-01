package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MyEffect {
    String name;
    Vector2 pos;
    float life;
    float timer;

    public MyEffect(String name, Vector2 pos, float life) {
        this.name = name;
        this.pos = pos;
        this.life = life;
        timer = 0;
    }

    void update() {
        timer += Gdx.graphics.getDeltaTime();
    }
}
