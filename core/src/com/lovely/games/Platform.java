package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.lovely.games.CaenMain.RANDOM_SOUND_ID_RANGE;
import static com.lovely.games.CaenMain.TILE_SIZE;

public class Platform implements Switchable {

    public static final float SPEED = TILE_SIZE * 2.0f;

    Vector2 pos;
    Vector2 start;
    Vector2 end;
    Vector2 destination;
    float offset;
    float timer;
    boolean isActive;
    String switchId;
    boolean initialIsActive;
    Color color;
    private int soundId;
    private float animTimer;
    float smokeTimer;
    float smokeTotal;

    public Platform(Vector2 start, Vector2 end, float offset, boolean isActive, String switchId) {
        this.pos = start.cpy();
        this.start = start;
        this.end = end;
        this.destination = end;
        this.offset = offset;
        this.timer = 0;
        this.isActive = isActive;
        this.initialIsActive = isActive;
        this.switchId = switchId;
        this.color = new Color(random(0.7f, 0.8f), random(0.5f, 0.6f), random(0.4f, 0.5f), 1.0f);
        this.soundId = MathUtils.random(RANDOM_SOUND_ID_RANGE);
        animTimer = MathUtils.random(4.0f);
    }

    void start(SoundPlayer soundPlayer) {
        timer = -offset;
        pos = start.cpy();
        destination = end;
        isActive = initialIsActive;
//        soundPlayer.playSound(soundId, "mechanical-1.ogg", pos, true);
        smokeTimer = MathUtils.random(8f);
        smokeTotal = MathUtils.random(8f);
    }

    public void update(SoundPlayer soundPlayer, CaenMain theFirstGate) {
        if (isActive) {
            animTimer = animTimer + Gdx.graphics.getDeltaTime();
            smokeTimer += Gdx.graphics.getDeltaTime();
            if (smokeTimer > smokeTotal) {
                smokeTimer = 0;
                smokeTotal = MathUtils.random(8f);
                theFirstGate.addEffect("entity/platform-particle-1.png", pos.cpy().add(8,8).add(getMovement().cpy().scl(8)), 0.8f);
            }
            if (timer < 0) {
                timer = timer + Gdx.graphics.getDeltaTime();
            } else {
                pos.add(getMovement());

                if (destination.dst2(pos) < 1) {
                    if (destination.equals(end)) {
                        destination = start;
                    } else {
                        destination = end;
                    }
                }
            }
            soundPlayer.playSound(soundId, "music/platform-4.ogg", pos, false);
        } else {
            soundPlayer.stopSound(soundId);
        }
    }

    public Vector2 getMovement() {
        Vector2 dir = new Vector2();
        if (isActive) {
            dir = destination.cpy().sub(pos).nor();
        }
        return dir.scl(SPEED * Gdx.graphics.getDeltaTime());
    }

    public void handleMessage(String id) {
        if (this.switchId != null && this.switchId.equals(id)) {
            isActive = !isActive;
        }
    }

    public float getAnimTimer() {
        return animTimer;
    }
}
