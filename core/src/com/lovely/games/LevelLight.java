package com.lovely.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class LevelLight implements Switchable {

    public Vector2 pos;
    public Vector2 size;
    public Color color;
    String switchId;
    public boolean isActive;

    public LevelLight(Vector2 pos, Vector2 size, Color color, String switchId) {
        this.pos = pos;
        this.size = size;
        this.color = color;
        this.switchId = switchId;
        this.isActive = true;
    }

    @Override
    public void handleMessage(String id) {
        if (switchId != null && id.equals(switchId)) {
            this.isActive = !isActive;
        }
    }
}
