package com.lovely.games.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.Switchable;

public class Torch implements Switchable {

    public Vector2 pos;
    public Color color;
    public boolean isFire;
    public boolean isOn;
    boolean originalIsOn;
    String switchId;

    public Torch(Vector2 pos, Color color, boolean isFire, String switchId, boolean isOn) {
        this.pos = pos;
        this.color = color;
        this.isFire = isFire;
        this.isOn = isOn;
        this.originalIsOn = isOn;
        this.switchId = switchId;
    }

    public void start() {
        this.isOn = originalIsOn;
    }

    @Override
    public void handleMessage(String id) {
        if (switchId != null && switchId.equals(id)) {
            isOn = !isOn;
        }
    }
}
