package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class ScreenFader {

    private boolean inDirection;
    private float amount;
    private boolean isDone;
    private float time;
    private Color color;

    public ScreenFader() {
        isDone = true;
    }

    public void fadeScreen(boolean inDirection, float time, Color color) {
        this.inDirection = inDirection;
        this.time = time;
        this.color = color;
        this.amount = inDirection ? time : 0;
        this.isDone = false;
    }

    public void update(Stage stage) {
        if (!isDone) {
            if (inDirection) {
                amount = amount - Gdx.graphics.getDeltaTime();
                if (amount < 0) {
                    isDone = true;
                    amount = 0;
                }
            } else {
                amount = amount + Gdx.graphics.getDeltaTime();
                if (amount > time) {
                    isDone = true;
                    amount = time;
                }
            }
            stage.setScreenFade(amount / time, color);
        }
    }
}
