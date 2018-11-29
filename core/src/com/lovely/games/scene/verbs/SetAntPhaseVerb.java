package com.lovely.games.scene.verbs;

import com.lovely.games.Actor;
import com.lovely.games.Stage;

public class SetAntPhaseVerb implements SceneVerb {

    boolean isDone;
    Actor.Phase phase;

    public SetAntPhaseVerb(Actor.Phase phase) {
        this.phase = phase;
    }

    @Override
    public void start() {
        isDone = false;
    }

    @Override
    public void update(Stage stage) {
        if (!isDone) {
            stage.setAntPhase(phase);
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

    public void skip() {

    }
}
