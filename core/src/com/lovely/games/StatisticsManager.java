package com.lovely.games;

import com.badlogic.gdx.Gdx;

import java.util.LinkedList;
import java.util.Queue;

public class StatisticsManager {

    private static final float UPDATE_TICK = 2f;
    private static final int NUM_EVENTS_PER_TICK = 5;
    Queue<GameEvent> events;
    int pid;
    float timer;

    public StatisticsManager(int pid) {
        this.pid = pid;
        events = new LinkedList<>();
        timer = 0;
    }

    public void addGameEvent(GameEvent event) {
        events.add(event);
    }


    public String getCurrentTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public void update() {
        timer = timer + Gdx.graphics.getDeltaTime();
        if (timer > UPDATE_TICK) {
            pushEvents();
            timer = timer - UPDATE_TICK;
        }
    }

    private void pushEvents() {
        for (int i = 0; i < NUM_EVENTS_PER_TICK && events.size() > 0; i++) {
            GameEvent event = events.poll();
            // TODO send this event
//            System.out.println(event.asString());
        }
    }

    public GameEvent startLevelEvent(Level level) {
        return new GameEvent("start-level", getCurrentTime(), pid, level.name, level.number);
    }

    public GameEvent playerDeathEvent(String reason, Level level) {
        return new GameEvent("player-death", getCurrentTime(), pid, reason, level.number);
    }
}
