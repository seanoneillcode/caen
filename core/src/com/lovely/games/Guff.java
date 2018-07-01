package com.lovely.games;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Guff {

    float offset;
    String imageName;
    Vector2 pos;
    Vector2 size;

    public Guff(String imageName, Vector2 pos, Vector2 size) {
        this.imageName = imageName;
        this.pos = pos;
        this.size = size;
        this.offset = MathUtils.random(0, 5.0f);
    }
}
