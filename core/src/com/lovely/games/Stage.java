package com.lovely.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.scene.DialogVerb;

public interface Stage {

    void startDialog(String id, DialogVerb dialogVerb);

    void setActorPos(String actor, Vector2 pos);

    void moveCamera(Vector2 pos);

    void resetCamera();

    Trunk getTrunk();

    void moveActor(String actor, Vector2 value);

    void hideActor(String actor, boolean isHide);

    void showPoster(float alpha, String poster);

    void goToConnection(String connection);

    void setScreenFade(float amount, Color color);

    void fadeScreen(boolean inDirection, float time, Color color);

    void gotoState(String state);

    void playSound(int id, String name, Vector2 pos);
}
