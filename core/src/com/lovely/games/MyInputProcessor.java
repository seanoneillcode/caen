package com.lovely.games;

import java.util.HashMap;
import java.util.Map;

public class MyInputProcessor {

    public Map<String, Integer> keyMappings;
    public int lastKeyCode = 0;
    public boolean hasInput = false;

    public MyInputProcessor() {
        keyMappings = new HashMap<>();
    }

    public void acceptInput() {
        hasInput = false;
        lastKeyCode = 0;
    }

    public void setNewKey(String pressKeyPlease) {
        int lastKey = lastKeyCode;
        keyMappings.put(pressKeyPlease, lastKey);
    }
}