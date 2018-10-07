package com.lovely.games.scene;

import com.lovely.games.Stage;

public class HideShowImageVerb implements SceneVerb {

    boolean hide;
    boolean isDone;
    String actor;
    boolean originalHide;

    public HideShowImageVerb(boolean hide, String actor) {
        this.hide = hide;
        this.isDone = false;
        this.actor = actor;
        this.originalHide = hide;
    }

    @Override
    public void start() {
        isDone = false;
        hide = originalHide;
    }

    @Override
    public void update(Stage stage) {
        if (!isDone) {
            stage.hideGuff(actor, hide);
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
