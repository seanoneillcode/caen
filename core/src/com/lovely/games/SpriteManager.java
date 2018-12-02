package com.lovely.games;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class SpriteManager {

    private Map<String, Sprite> sprites;
    private AssetManager assetManager;

    public SpriteManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        sprites = new HashMap<>();
        load();
    }

    private void load() {
        addSprite("lightHole", "light-hole.png", 6f, 6f);
        addSprite("playerLight", "player-light.png", 2f, 2f);
        addSprite("levelLight", "level-light.png", 1f, 1f);
        addSprite("fadeSprite", "fade-image.png", 8f, 8f);
        addSprite("posterSprite", "posters/poster-prize.png", 6f, 6f);
        addSpriteWithSize("enemySprite", "entity/enemy.png", 40, 40);
        addSpriteWithSize("selectArrowSprite", "select-arrow.png", 32, 32);
        addSpriteWithSize("arrowSourceSprite", "entity/arrow-source.png", 32, 48);
        addSpriteWithSize("doorSprite", 64, 64);
        addSpriteWithSize("menuSprite", "posters/menu-sprites.png", 800, 400);
        addSpriteWithSize("playerSprite", 32, 32);
        addSpriteWithSize("androidSprite", "pointer.png", 5, 5);
        addSpriteWithSize("antSprite", 32, 32);
        addSpriteWithSize("blockSprite", "entity/block.png", 32, 48);
        addSpriteWithSize("groundBlockSprite", "entity/ground-block.png", 32, 32);
        addSprite("titleSprite", "caen-title.png", 2f, 2f);
        addSprite("volumePointerSprite", "volume-pointer.png", 2f, 2f);
        addSprite("volumeLevelOnSprite", "volume-level-on.png", 2f, 2f);
        addSprite("volumeLevelOffSprite", "volume-level-off.png", 2f, 2f);
        addSpriteWithSize("arrowSprite", 32, 32);

    }

    public Sprite getSprite(String name) {
        return sprites.get(name);
    }

    private void addSprite(String name, String textureName, float scaleX, float scaleY) {
        Sprite sprite = new Sprite((Texture) assetManager.get(textureName));
        sprite.setScale(scaleX, scaleY);
        sprites.put(name, sprite);
    }

    private void addSpriteWithSize(String name, String textureName, int sizeX, int sizeY) {
        Sprite sprite = new Sprite((Texture) assetManager.get(textureName));
        sprite.setSize(sizeX, sizeY);
        sprites.put(name, sprite);
    }

    private void addSpriteWithSize(String name, int sizeX, int sizeY) {
        Sprite sprite = new Sprite();
        sprite.setSize(sizeX, sizeY);
        sprites.put(name, sprite);
    }
}
