package com.lovely.games.scene;

import com.badlogic.gdx.math.Vector2;
import com.lovely.games.Switchable;

public class SceneSource implements Switchable {

    public Vector2 pos;
    public String id;
    public Vector2 size;
    public boolean isDone;
    boolean playOnce;
    public boolean isActive;
    boolean originalIsActive;
    String switchId;

    public SceneSource(Vector2 pos, String id, Vector2 size, boolean playOnce, boolean isActive, String switchId) {
        this.pos = pos;
        this.id = id;
        this.switchId = switchId;
        this.size = size;
        this.isDone = false;
        this.isActive = isActive;
        this.playOnce = playOnce;
        this.originalIsActive = isActive;
    }

    public void start() {
        if (!playOnce) {
            this.isDone = false;
            this.isActive = originalIsActive;
        }
    }

    @Override
    public void handleMessage(String id) {
        if (switchId != null && id.equals(switchId)) {
            this.isActive = !isActive;
        }
    }

    public boolean isPlayOnce() {
        return playOnce;
    }
}
