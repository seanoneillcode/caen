package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import static com.lovely.games.Constants.TILE_SIZE;

public class Wind {

    private static final float WIND_SPEED = 64.0f;
    private static final float LEEWAY = 32.0f;

    private final int numWinds;
    Vector2 pos;
    Vector2 size;
    String dir;
    List<Vector2> drawPositions;
    float variance = 0;
    float targetVariance = 0;

    public Wind(Vector2 pos, Vector2 size, String dir) {
        this.pos = pos;
        this.size = size;
        this.dir = dir;
        numWinds = 1 + (int)((size.x / TILE_SIZE) * (size.y / TILE_SIZE) / 2.0f);
        this.drawPositions = new ArrayList<>();
        for ( int i = 0; i < numWinds; i++) {
            drawPositions.add(new Vector2(MathUtils.random(pos.x, pos.x + size.x), MathUtils.random(pos.y, pos.y + size.y)));
        }
    }

    public void update() {
        float i = 1;
        for (Vector2 drawPos : drawPositions) {
            i = i + (1.0f / drawPositions.size());
            if (variance < targetVariance) {
                variance = variance + 0.01f;
            }
            if (variance > targetVariance) {
                variance = variance - 0.01f;
            }
            if (Math.abs(variance - targetVariance) < 1.1f) {
                targetVariance = MathUtils.random(-32.0f, 32.0f);
            }
            Vector2 mov = getDir().scl(Gdx.graphics.getDeltaTime() * ((WIND_SPEED * i) + variance));
            drawPos.add(mov);
            if (drawPos.x > pos.x + size.x + LEEWAY) {
                drawPos.x = pos.x - LEEWAY;
            }
            if (drawPos.y > pos.y + size.y + LEEWAY) {
                drawPos.y = pos.y - LEEWAY;
            }
            if (drawPos.x < pos.x - LEEWAY) {
                drawPos.x = pos.x + size.x + LEEWAY;
            }
            if (drawPos.y < pos.y - LEEWAY) {
                drawPos.y = pos.y + size.y + LEEWAY;
            }
        }
    }

    public boolean isHorizontal() {
        return dir.equals("left") || dir.equals("right");
    }

    public Vector2 getDir() {
        switch(dir) {
            case "left":
                return new Vector2(-1,0);
            case "right":
                return new Vector2(1,0);
            case "up":
                return new Vector2(0,1);
            case "down":
                return new Vector2(0,-1);
        }
        return null;
    }
}
