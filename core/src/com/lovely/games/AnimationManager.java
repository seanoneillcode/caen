package com.lovely.games;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager {

    private Map<String, Animation<TextureRegion>> guffImages;

    Animation<TextureRegion> walkRight, idleAnim, pressureOnAnim, pressureOffAnim;
    Animation<TextureRegion> lightAnim, playerLightAnim, arrowAnim, torchAnim, campfireAnim, doorOpenAnim, doorCloseAnim;
    Animation<TextureRegion> fireDeath;
    Animation<TextureRegion> fallDeath;
    Animation<TextureRegion> pushBlock;
    Animation<TextureRegion> playerShoot;
    Animation<TextureRegion> arrowExplodeAnim;
    Animation<TextureRegion> antWalk;
    Animation<TextureRegion> antIdle;
    Animation<TextureRegion> openingScene;
    Animation<TextureRegion> platformAnim;
    Animation<TextureRegion> arrowSourceAnim;
    Animation<TextureRegion> walkUp, walkDown, enemyIdle, enemyShoot, menuSpriteAnim;
    Animation<TextureRegion> switchOnAnim, switchOffAnim;
    Animation<TextureRegion> doorAcrossOpenAnim, doorAcrossCloseAnim;
    Animation<TextureRegion> doorDustAnim;
    Animation<TextureRegion> antDrink, antFall;
    Animation<TextureRegion> selectArrowAnim;

    public void load(AssetManager assetManager) {
        antWalk = loadAnimation(assetManager.get("character/ant-walk.png"), 4, 0.165f);
        antIdle = loadAnimation(assetManager.get("character/ant-idle.png"), 2, 0.5f);
        antDrink = loadAnimation(assetManager.get("character/ant-simple-drink.png"), 14, 0.2f);
        antFall = loadAnimation(assetManager.get("character/ant-simple-fall.png"), 9, 0.1f);
        enemyIdle = loadAnimation(assetManager.get("entity/enemy-idle.png"), 4, 0.25f);
        enemyShoot = loadAnimation(assetManager.get("entity/enemy-shoot.png"), 4, 0.05f);
        walkRight = loadAnimation(assetManager.get("character/pro-simple-walk.png"), 4, 0.16f); // 0.165
        walkUp = loadAnimation(assetManager.get("character/pro-simple-walk-up.png"), 4, 0.16f); // 0.165
        walkDown = loadAnimation(assetManager.get("character/pro-simple-walk-down.png"), 4, 0.16f); // 0.165
        fireDeath = loadAnimation(assetManager.get("character/pro-simple-fire-death.png"), 8, 0.085f);
        fallDeath = loadAnimation(assetManager.get("character/pro-simple-fall-death.png"), 5, 0.08f);
        playerShoot = loadAnimation(assetManager.get("character/pro-simple-shoot.png"), 6, 0.07f);
        pushBlock = loadAnimation(assetManager.get("character/pro-simple-push.png"), 4, 0.165f);
        idleAnim = loadAnimation(assetManager.get("character/pro-simple-idle.png"), 2, 0.5f);
        lightAnim = loadAnimation(assetManager.get("light-magic.png"), 4, 0.6f);
        playerLightAnim = loadAnimation(assetManager.get("player-light.png"), 4, 0.5f);
        arrowAnim = loadAnimation(assetManager.get("entity/arrow-sheet.png"), 8, 0.05f);
        arrowExplodeAnim = loadAnimation(assetManager.get("entity/arrow-explode.png"), 8, 0.05f);
        torchAnim = loadAnimation(assetManager.get("entity/torch-anim.png"), 8, 0.2f);
        campfireAnim = loadAnimation(assetManager.get("entity/campfire.png"), 8, 0.1f);
        doorAcrossOpenAnim = loadAnimation(assetManager.get("levels/door-horizontal.png"), 8, 0.03f);
        doorAcrossCloseAnim = loadAnimation(assetManager.get("levels/door-horizontal.png"), 8, 0.03f);
        doorOpenAnim = loadAnimation(assetManager.get("levels/door-vertical.png"), 8, 0.03f);
        doorDustAnim = loadAnimation(assetManager.get("levels/door-dust.png"), 8, 0.08f);
        doorCloseAnim = loadAnimation(assetManager.get("levels/door-vertical.png"), 8, 0.03f);
        pressureOnAnim = loadAnimation(assetManager.get("entity/pressure-on.png"), 4, 0.02f);
        pressureOffAnim = loadAnimation(assetManager.get("entity/pressure-on.png"), 4, 0.02f);
        switchOnAnim = loadAnimation(assetManager.get("entity/switch-on.png"), 4, 0.02f);
        switchOffAnim = loadAnimation(assetManager.get("entity/switch-on.png"), 4, 0.02f);
        platformAnim = loadAnimation(assetManager.get("entity/platform-anim.png"), 8, 0.1f);
        arrowSourceAnim = loadAnimation(assetManager.get("entity/arrow-source.png"), 8, 0.1f);
        openingScene = loadAnimation(assetManager.get("player-large.png"), 8, 0.3f);
        menuSpriteAnim = loadAnimation(assetManager.get("posters/menu-sprites.png"), 12, 0.2f);
        selectArrowAnim = loadAnimation(assetManager.get("select-arrow.png"), 2, 0.4f);
        doorOpenAnim.setPlayMode(Animation.PlayMode.REVERSED);
        doorAcrossCloseAnim.setPlayMode(Animation.PlayMode.REVERSED);
        pressureOffAnim.setPlayMode(Animation.PlayMode.REVERSED);
        switchOffAnim.setPlayMode(Animation.PlayMode.REVERSED);

        guffImages = new HashMap<>();
        guffImages.put("entity/grass-1.png", loadAnimation(assetManager.get("entity/grass-1.png"), 4, 0.515f));
        guffImages.put("entity/grass-2.png", loadAnimation(assetManager.get("entity/grass-2.png"), 4, 0.52f));
        guffImages.put("entity/grass-3.png", loadAnimation(assetManager.get("entity/grass-3.png"), 4, 0.525f));
        guffImages.put("entity/grass-4.png", loadAnimation(assetManager.get("entity/grass-4.png"), 4, 0.53f));
        guffImages.put("entity/dust-air.png", loadAnimation(assetManager.get("entity/dust-air.png"), 16, 0.2f));
        guffImages.put("character/dead.png", loadAnimation(assetManager.get("character/dead.png"), 1, 1f));
        guffImages.put("entity/lintel.png", loadAnimation(assetManager.get("entity/lintel.png"), 1, 1f));
        guffImages.put("entity/heavy-door.png", loadAnimation(assetManager.get("entity/heavy-door.png"), 1, 1f));
        guffImages.put("entity/crystal.png", loadAnimation(assetManager.get("entity/crystal.png"), 1, 1f));
        guffImages.put("entity/rain.png", loadAnimation(assetManager.get("entity/rain.png"), 4, 0.1f));
        guffImages.put("entity/dust-air-2.png", loadAnimation(assetManager.get("entity/dust-air-2.png"), 16, 0.2f));
        guffImages.put("entity/platform-particle-1.png", loadAnimation(assetManager.get("entity/platform-particle-1.png"), 8, 0.1f));
    }

    private Animation<TextureRegion> loadAnimation(Texture sheet, int numberOfFrames, float frameDelay) {
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / numberOfFrames, sheet.getHeight());
        Array<TextureRegion> frames = new Array<>(numberOfFrames);
        for (int i = 0; i < numberOfFrames; i++) {
            frames.add(tmp[0][i]);
        }
        return new Animation<>(frameDelay, frames);
    }

    public Animation<TextureRegion> getGuff(String name) {
        return guffImages.get(name);
    }
}
