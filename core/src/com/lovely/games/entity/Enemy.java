package com.lovely.games.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.CaenMain;

import static com.lovely.games.Constants.HALF_TILE_SIZE;

public class Enemy extends Block implements BlockLike {

    Vector2 dir;
    public Color color;
    String stringDir;
    float coolDown = 0f;
    float MAX_COOLDOWN = 0.02f;
    public boolean isShooting;

    public Enemy(Vector2 pos, String dir) {
        super(pos);
        this.stringDir = dir;
        this.dir = getDir(dir);
        this.color = new Color(0.4f, 0.8f, 0.7f, 1.0f);
        isShooting = false;
    }

    public void start() {
        super.start();
        isShooting = false;
    }

    private Vector2 getDir(String dir) {
        switch (dir) {
            case "left":
                return new Vector2(-1, 0);
            case "right":
                return new Vector2(1, 0);
            case "up":
                return new Vector2(0 ,1);
            case "down":
                return new Vector2(0,-1);
        }
        return new Vector2();
    }

    public void update(Vector2 playerPos, CaenMain theFirstGate) {
        super.update();
        boolean colliding = false;
        boolean prevShooting = isShooting;
        isShooting = false;
        if (!isGround) {
            Vector2 checkPos = pos.cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE);
            for (int i = 0; i < 64; i++) {
                checkPos.add(dir.cpy().scl(HALF_TILE_SIZE));
                if (theFirstGate.isLazerBlocking(checkPos.cpy())){
                    break;
                }
                if (contains(checkPos, new Vector2(HALF_TILE_SIZE, HALF_TILE_SIZE), playerPos)) {
                    colliding = true;
                    if (!prevShooting) {
                        animTimer = 0;
                    }
                    isShooting = true;
                    break;
                }
            }
            if (colliding) {
                if (coolDown < 0) {
                    theFirstGate.addLazer(pos.cpy().add(dir.x, dir.y + 8), dir.cpy());
                    coolDown = MAX_COOLDOWN;
                }
            }
        }
        coolDown = coolDown - Gdx.graphics.getDeltaTime();
    }

    private boolean contains(Vector2 checkPos, Vector2 size, Vector2 player) {
        Vector2 halfSize = size.scl(0.5f);
        return player.x > (checkPos.x - halfSize.x)
                && player.x < (checkPos.x + halfSize.x)
                && player.y > (checkPos.y - halfSize.y)
                && player.y < (checkPos.y + halfSize.y);
    }

    public float getRotation() {
        switch (stringDir) {
            case "left":
                return 270;
            case "right":
                return 90;
            case "up":
                return 180;
            case "down":
                return 0;
        }
        return 0;
    }
}
