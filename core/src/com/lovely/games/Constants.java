package com.lovely.games;

import com.badlogic.gdx.math.MathUtils;

public class Constants {

    public static final float TILE_SIZE = 32f;
    public static final float ARROW_SPEED = TILE_SIZE * 2.0f;
    public static final float HALF_TILE_SIZE = 16f;
    public static final float QUARTER_TILE_SIZE = 8f;
    public static final float PLAYER_SPEED = TILE_SIZE * 4.0f;
    public static final float CAMERA_MARGIN = 0.5f;
    public static final float CAMERA_CATCHUP_SPEED = 3.0f;
    public static final int VIEWPORT_WIDTH = 800;
    public static final int VIEWPORT_HEIGHT = 480;
    public static final float CAST_ARROW_COOLDOWN = 0.6f;
    public static final float PLAYER_DEATH_TIME = 1.0f;
    public static final float PLAYER_SHOOTING_TIME = 0.34f;
    public static final float PLAYER_ARROW_SPEED = TILE_SIZE * 4.0f;
    public static final float ZOOM_AMOUNT = 0.005f;
    public static final float ZOOM_THRESHOLH = 0.01f;
    public static final float LEVEL_TRANSITION_TIMER = 0.5f;
    public static final float PLAYER_TRANSITION_SPEED = 0.5f;
    public static final int START_LEVEL_NUM = 46;
    public static final int RANDOM_SOUND_ID_RANGE = 1000000;
    public static final int BLIP_SELECT_ITEM_SOUND_ID = MathUtils.random(RANDOM_SOUND_ID_RANGE);
    public static final float DEFAULT_SOUND_LEVEL = 0.2f;
    public static final float DEFAULT_MUSIC_LEVEL = 1f;
    public static final float DEFAULT_GAMMA = 0.3f;
}
