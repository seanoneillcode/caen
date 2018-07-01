package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class MyInputProcessor implements InputProcessor {

    public int lastKeyCode = 0;
    public boolean hasInput = false;
    public Camera camera;
    public boolean hasTouchInput = false;

    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    private Map<Integer, TouchInfo> touches = new HashMap<>();

    public Vector2 getAndroidPos() {
        return new Vector2(touches.get(0).touchX, touches.get(0).touchY);
    }

    public MyInputProcessor(Camera camera) {
        for(int i = 0; i < 5; i++){
            touches.put(i, new TouchInfo());
        }
        this.camera = camera;
    }

    public void acceptInput() {
        hasInput = false;
        lastKeyCode = 0;
    }

    public boolean keyDown (int keycode) {
        hasInput = true;
        lastKeyCode = keycode;
        return false;
    }

    public boolean keyUp (int keycode) {
        lastKeyCode = keycode;
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {

//Transform to world coordinates using the correct camera
        hasTouchInput = true;
        if(pointer < 5){

            Vector3 mousePosition = new Vector3(x, y, 0);
            mousePosition = camera.unproject(mousePosition);
            touches.get(pointer).touchX = mousePosition.x;
            touches.get(pointer).touchY = mousePosition.y;
            touches.get(pointer).touched = true;
        }
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }
}