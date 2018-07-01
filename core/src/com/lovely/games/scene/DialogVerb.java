package com.lovely.games.scene;

import com.lovely.games.Stage;

public class DialogVerb implements SceneVerb {

    boolean isDone;
    boolean conversationStarted;
    String id;
    private String outcome;

    public DialogVerb(String id) {
        this.isDone = false;
        this.conversationStarted = false;
        this.id = id;
    }

    @Override
    public void start() {
        isDone = false;
        this.conversationStarted = false;
    }

    @Override
    public void update(Stage stage) {
        if (!conversationStarted) {
            conversationStarted = true;
            stage.startDialog(id, this);
        }
    }

    public void finish(String outcome) {
        isDone = true;
        this.outcome = outcome;
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

    @Override
    public String getOutcome() {
        return outcome;
    }
}
