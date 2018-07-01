package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import static com.lovely.games.CaenMain.TILE_SIZE;

public class Enemy extends Block implements BlockLike {

    Vector2 dir;
    Color color;
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

    void start() {
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
            Vector2 checkPos = pos.cpy();
            for (int i = 0; i < 10; i++) {
                checkPos.add(dir.cpy().scl(TILE_SIZE));
                if (theFirstGate.isArrowBlocking(checkPos)) {
                    break;
                }
                if (contains(checkPos.cpy().add(8, 8), new Vector2(16, 16), playerPos)) {
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
        return player.x > checkPos.x && player.x < (checkPos.x + size.x)
                && player.y > checkPos.y && player.y < (checkPos.y + size.x);
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
