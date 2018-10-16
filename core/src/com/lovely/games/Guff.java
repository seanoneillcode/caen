package com.lovely.games;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Guff {

    private final boolean originalHide;
    float offset;
    String imageName;
    Vector2 pos;
    Vector2 size;
    private boolean isOnTop;
    public boolean hide;

    public Guff(String imageName, Vector2 pos, Vector2 size, boolean isOnTop, boolean hide) {
        this.imageName = imageName;
        this.pos = pos;
        this.size = size;
        this.isOnTop = isOnTop;
        this.offset = MathUtils.random(0, 5.0f);
        this.hide = hide;
        this.originalHide = hide;
    }

    public void reset() {
        this.hide = originalHide;
    }

    public boolean isOnTop() {
        return isOnTop;
    }

    public void setVisible(boolean hide) {
        this.hide = hide;
    }
}
