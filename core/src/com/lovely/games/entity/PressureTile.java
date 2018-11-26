package com.lovely.games.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.SoundPlayer;
import com.lovely.games.Trunk;

import static com.badlogic.gdx.math.MathUtils.random;

public class PressureTile {

    public Vector2 pos;
    Trunk trunk;
    private boolean handledAction;
    public String switchId;
    public boolean isSwitch;
    public Color color;
    public float animTimer;
    public boolean isPressure;

    public PressureTile(Vector2 pos, String switchId, boolean isSwitch) {
        this.pos = pos;
        this.handledAction = false;
        this.switchId = switchId;
        this.trunk = null;
        this.isSwitch = isSwitch;
        this.color = new Color(random(0.8f, 1.0f), random(0.2f, 0.4f), random(0.3f, 0.5f), 1.0f);
        animTimer = 0;
        isPressure = false;
    }

    public void setTrunk(Trunk trunk) {
        this.trunk = trunk;
    }

    public void start() {
        this.handledAction = false;
        isPressure = false;
        animTimer = 10;
    }

    public void update() {
        animTimer += Gdx.graphics.getDeltaTime();
    }

    public void handlePressureOff(SoundPlayer soundPlayer) {
        if (handledAction) {
            if (!isSwitch) {
                if (trunk != null) {
                    trunk.broadcast(switchId);
                }
                isPressure = false;
                animTimer = 0;
            }
        }
        this.handledAction = false;
    }

    public void handleAction(SoundPlayer soundPlayer) {
        if (!handledAction && trunk != null) {
            trunk.broadcast(switchId);
            handledAction = true;
            isPressure = !isPressure;
            soundPlayer.playSound("music/thunk.ogg", pos);
            animTimer = 0;
        }
    }
}
