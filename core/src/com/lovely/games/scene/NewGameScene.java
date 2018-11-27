package com.lovely.games.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.CaenMain;

import java.util.ArrayList;
import java.util.List;

public class NewGameScene {

    private static final float DARK_START = 2f;
    public static final float START_TIME_AMOUNT = DARK_START + 3.0f;
    public static final float MIDDLE_TIME_AMOUNT = START_TIME_AMOUNT + 3.0f;
    public static final float END_TIME_AMOUNT = MIDDLE_TIME_AMOUNT + 2f;
    public static final float TOTAL_TIME = END_TIME_AMOUNT;

    List<Phase> phases = new ArrayList<>();

    Animation<TextureRegion> standing;
    Sprite sprite;

    float animTimer;
    private float alpha;
    boolean isDone;
    Vector2 pos;
    int phaseIndex;

    public NewGameScene(Animation<TextureRegion> standing) {
        this.standing = standing;
        sprite = new Sprite(standing.getKeyFrame(0));
        sprite.setScale(2.0f);
        phases.add(new Phase(2f, "dark-start"));
        phases.add(new Phase(4f, "start"));
        phases.add(new Phase(6f, "middle"));
        phases.add(new Phase(2f, "ending"));
        phaseIndex = 0;
    }

    public void reset() {
        animTimer = 0;
        sprite.setPosition(0, 0);
        alpha = 0;
        isDone = false;
        phaseIndex = 0;
        pos = new Vector2(0, -40);
        for (Phase phase : phases) {
            phase.timer = 0;
        }
    }

    public void update(CaenMain theFirstGate) {
        if (isDone) {
            return;
        }
        animTimer = animTimer + Gdx.graphics.getDeltaTime();
        Phase phase = phases.get(phaseIndex);
        phase.update();
        if (phase.isDone()) {
            phaseIndex++;
            if (phaseIndex == phases.size()) {
                isDone = true;
                theFirstGate.isPlayingOpeningScene = false;
                theFirstGate.gotoState("new-game");
                return;
            }
        }
        if (phase.id.equals("dark-start")) {

        }
        if (phase.id.equals("start")) {
            alpha = phase.timer / phase.length;
//            pos.y = pos.y + (Gdx.graphics.getDeltaTime() * 10f);
        }
        if (phase.id.equals("middle")) {
            alpha = 1f;
        }
        if (phase.id.equals("ending")) {
            alpha = (phase.length - phase.timer) / phase.length;
        }
    }

    public void render(SpriteBatch batch, Vector2 cameraPos) {
        if (!isDone && !phases.get(phaseIndex).id.equals("dark-start")) {
            TextureRegion region = standing.getKeyFrame(animTimer, true);
            sprite.setRegion(region);
            sprite.setPosition(cameraPos.x + pos.x, cameraPos.y + pos.y);
            sprite.setAlpha(alpha);
            sprite.draw(batch);
        }
    }

    public void finish() {
        phaseIndex = phases.size() - 1;
    }

    private class Phase {
        float length;
        float timer;
        String id;

        public Phase(float length, String id) {
            this.length = length;
            timer = 0;
            this.id = id;
        }

        void update() {
            timer += Gdx.graphics.getDeltaTime();
        }

        boolean isDone() {
            return timer > length;
        }
    }
}
