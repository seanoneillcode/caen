package com.lovely.games;

import java.util.Arrays;
import java.util.List;

public class GameEvent {

    String type;
    String time;
    int pid;
    List<String> data;

    public GameEvent(String type, String time, int pid, String... data) {
        this.type = type;
        this.time = time;
        this.pid = pid;
        this.data = Arrays.asList(data);
    }

    public String asString() {
        StringBuilder sb = new StringBuilder();
        sb.append(pid);
        sb.append(" : ");
        sb.append(time);
        sb.append(" : ");
        sb.append(type);
        for (String datum : data) {
            sb.append(" : ");
            sb.append(datum);
        }
        return sb.toString();
    }
}
