package com.lovely.games.scene.verbs;

import com.badlogic.gdx.graphics.Color;
import com.lovely.games.Stage;

public class FadeScreenVerb implements SceneVerb {

    private boolean isDone;
    private boolean inDirection;
    private float amount;
    private float time;
    private float originalAmount;
    private Color color;

    public FadeScreenVerb(boolean inDirection, float time, Color color) {
        this.inDirection = inDirection;
        this.time = time;
        this.amount = inDirection ? time : 0;
        this.originalAmount = amount;
        this.isDone = false;
        this.color = color;
    }

    @Override
    public void update(Stage stage) {
        if (!isDone) {
//            if (inDirection) {
//                amount = amount - Gdx.graphics.getDeltaTime();
//                if (amount < 0) {
//                    isDone = true;
//                    amount = 0;
//                }
//            } else {
//                amount = amount + Gdx.graphics.getDeltaTime();
//                if (amount > time) {
//                    isDone = true;
//                    amount = time;
//                }
//            }
//            amount = amount - Gdx.graphics.getDeltaTime();
//            if (amount < 0) {
//                isDone = true;
//            }
            stage.fadeScreen(inDirection, time, color);
            isDone = true;
        }
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public void start() {
        isDone = false;
        amount = originalAmount;
    }

    @Override
    public void skip() {

    }
}
