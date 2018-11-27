package com.lovely.games.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.Stage;

public class StonePrizeScene {

    private boolean needsReset;
    Sprite player;
    Sprite light;
    Sprite stone;
    Sprite faceLight;

    float timer;
    private Vector2 playerPos, stonePos;
    private float lightAlpha;
    private boolean isIncrease;
    private float amount;

    public StonePrizeScene(AssetManager assetManager) {
        player = new Sprite((Texture)assetManager.get("posters/stone-0.png"));
        player.setScale(2.0f);
        stone = new Sprite((Texture)assetManager.get("posters/stone-2.png"));
        stone.setScale(2.0f);
        light = new Sprite((Texture)assetManager.get("posters/stone-1.png"));
        light.setScale(2.0f);
        faceLight = new Sprite((Texture)assetManager.get("posters/stone-3.png"));
        faceLight.setScale(2.0f);
        timer = 0;
        needsReset = true;
    }

    public void reset() {
        timer = 0;
        playerPos = new Vector2(-134f, -24f);
        stonePos = new Vector2(96f, -24f);
        lightAlpha = 0;
        amount = 0.01f;
        isIncrease = true;
        needsReset = false;
    }

    public void update(Stage stage) {
        timer = timer + Gdx.graphics.getDeltaTime();
        if (timer < 1.0f) {
            playerPos.x = playerPos.x + 2.2f;
            playerPos.y = playerPos.y + 0.3f;

            stonePos.x = stonePos.x - 1.6f;
            stonePos.y = stonePos.y + 0.3f;
        }
        if (timer > 1f ) {
            lightAlpha = lightAlpha + amount;
        }
        lightAlpha = MathUtils.clamp(lightAlpha, 0, 1.0f);
    }

    public void render(SpriteBatch batch, Vector2 pos, float alpha) {
        player.setPosition(pos.x + playerPos.x, pos.y + playerPos.y);
        player.draw(batch);
        player.setAlpha(alpha);
        light.setPosition(pos.x + stonePos.x - 150,pos.y + stonePos.y);
        light.setAlpha(MathUtils.clamp(lightAlpha * alpha, 0, 1f));
        light.draw(batch);
        faceLight.setPosition(pos.x + playerPos.x, pos.y + playerPos.y);
        faceLight.setAlpha(MathUtils.clamp(lightAlpha * alpha, 0, 1f));
        faceLight.draw(batch);
        stone.setAlpha(alpha);
        stone.setPosition(pos.x + stonePos.x,pos.y + stonePos.y);
        stone.draw(batch);
    }
}
