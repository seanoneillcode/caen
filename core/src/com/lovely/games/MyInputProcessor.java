package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class MyInputProcessor implements InputProcessor {

    public int lastKeyCode = 0;
    public boolean hasInput = false;
    public Camera camera;
    public boolean hasTouchInput = false;
    public Vector2 startJoyPos;
    public Vector2 joyVector;
    public float inputCaptureTimer = -1;
    public static final float INPUT_CAPTURE_WAIT = 0.2f;
    Vector2 startCameraPos;
    private Vector2 inputAmount = new Vector2();

    public Vector2 getStartJoyPos() {
        if (startJoyPos == null || startCameraPos == null) {
            return new Vector2();
        }
        Vector2 offset = new Vector2(startCameraPos.x - camera.position.x, startCameraPos.y - camera.position.y);
        return startJoyPos.cpy().sub(offset);
    }

    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    public MyInputProcessor(Camera camera) {
        for(int i = 0; i < 5; i++){
            touches.put(i, new TouchInfo());
        }
        this.camera = camera;
        joyVector = new Vector2();
        startCameraPos = null; //new Vector2(camera.position.x, camera.position.y);
    }

    private Map<Integer, TouchInfo> touches = new HashMap<>();

    public Vector2 getInputAmount()  {
        return inputAmount;
    }

    public void update() {
        if (inputCaptureTimer >= 0) {
            inputCaptureTimer = inputCaptureTimer - Gdx.graphics.getDeltaTime();
        }
        if (inputCaptureTimer < 0 && !joyVector.isZero()) {

            hasTouchInput = true;
            inputAmount.x = MathUtils.clamp(Math.abs(joyVector.x), 0, 32f) / 32f;
            inputAmount.y = MathUtils.clamp(Math.abs(joyVector.y), 0, 32f) / 32f;

        } else {
            inputAmount = new Vector2();
            hasTouchInput = false;
        }
    }

    public Vector2 getAndroidPos() {
        return new Vector2(touches.get(0).touchX, touches.get(0).touchY);
    }



    public Vector2 getJoyVector() {
        return joyVector.cpy();
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
//        hasTouchInput = true;
//        if (inputCaptureTimer < 0) {
//            hasTouchInput = true;
//            inputCaptureTimer = INPUT_CAPTURE_WAIT;
//        }
        Vector3 mousePosition = new Vector3(x, y, 0);
        mousePosition = camera.unproject(mousePosition);
        if(pointer < 5){


            touches.get(pointer).touchX = mousePosition.x;
            touches.get(pointer).touchY = mousePosition.y;
            touches.get(pointer).touched = true;
        }
        if (pointer == 0) {
            startJoyPos = new Vector2(mousePosition.x, mousePosition.y);
            startCameraPos = new Vector2(camera.position.x, camera.position.y);
        }
        return true;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        if (pointer == 0) {
            startJoyPos = null;
            startCameraPos = null;
            joyVector.x = 0;
            joyVector.y = 0;

        }
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        Vector3 mousePosition = new Vector3(x, y, 0);

        mousePosition = camera.unproject(mousePosition);
//        if (pointer == 0) {
//            System.out.println("x " + x + " y " + y + " pointer " + pointer);
            if (pointer == 0) {
                joyVector = new Vector2(mousePosition.x, mousePosition.y).sub(getStartJoyPos());
//                if (inputCaptureTimer < 0) {
//
//                    inputCaptureTimer = INPUT_CAPTURE_WAIT;
//                }

//                System.out.println(joyVector);
            }
//        }
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }
}