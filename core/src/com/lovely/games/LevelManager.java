package com.lovely.games;

import com.badlogic.gdx.assets.AssetManager;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private List<Level> levels;

    public LevelManager(AssetManager assetManager, SoundPlayer soundPlayer) {
        levels = new ArrayList<>();
        levels.add(Level.loadLevel(assetManager, "levels/tower-01.tmx", soundPlayer)); // 01
        levels.add(Level.loadLevel(assetManager, "levels/tower-02.tmx", soundPlayer));
        levels.add(Level.loadLevel(assetManager, "levels/tower-arrow-01.tmx", soundPlayer)); // 05
        levels.add(Level.loadLevel(assetManager, "levels/tower-arrow-02.tmx", soundPlayer)); // 07
        levels.add(Level.loadLevel(assetManager, "levels/tower-arrow-03.tmx", soundPlayer)); // 09
        levels.add(Level.loadLevel(assetManager, "levels/tower-arrow-04.tmx", soundPlayer)); // 11
        levels.add(Level.loadLevel(assetManager, "levels/tower-platform-01.tmx", soundPlayer)); // 13
        levels.add(Level.loadLevel(assetManager, "levels/tower-platform-02.tmx", soundPlayer)); // 15
        levels.add(Level.loadLevel(assetManager, "levels/tower-platform-03.tmx", soundPlayer)); // 17
        levels.add(Level.loadLevel(assetManager, "levels/tower-platform-04.tmx", soundPlayer)); // 19
        levels.add(Level.loadLevel(assetManager, "levels/tower-block-01.tmx", soundPlayer)); // 21 // 10
        levels.add(Level.loadLevel(assetManager, "levels/tower-block-02.tmx", soundPlayer)); // 23
        levels.add(Level.loadLevel(assetManager, "levels/tower-block-03.tmx", soundPlayer)); // 25
        levels.add(Level.loadLevel(assetManager, "levels/tower-block-04.tmx", soundPlayer)); // 27
        levels.add(Level.loadLevel(assetManager, "levels/tower-switch-01.tmx", soundPlayer)); // 29
        levels.add(Level.loadLevel(assetManager, "levels/tower-switch-02.tmx", soundPlayer)); // 31
        levels.add(Level.loadLevel(assetManager, "levels/tower-switch-03.tmx", soundPlayer)); // 33
        levels.add(Level.loadLevel(assetManager, "levels/tower-arrow-05.tmx", soundPlayer)); // 35
        levels.add(Level.loadLevel(assetManager, "levels/tower-switch-04.tmx", soundPlayer)); // 37
        levels.add(Level.loadLevel(assetManager, "levels/tower-switch-05.tmx", soundPlayer)); // 39
        levels.add(Level.loadLevel(assetManager, "levels/last-room.tmx", soundPlayer)); // 1 // 20
        levels.add(Level.loadLevel(assetManager, "levels/scene-test.tmx", soundPlayer)); // 1 // 22
        levels.add(Level.loadLevel(assetManager, "levels/tower-broken-level.tmx", soundPlayer)); // 51 // 23
        levels.add(Level.loadLevel(assetManager, "levels/tower-arrow-06.tmx", soundPlayer)); // 53 // 24
        levels.add(Level.loadLevel(assetManager, "levels/tower-bridge-1.tmx", soundPlayer)); // 55 // 25
        levels.add(Level.loadLevel(assetManager, "levels/tower-prize-fight.tmx", soundPlayer)); // 57 // 26
        levels.add(Level.loadLevel(assetManager, "levels/tower-ant-revenge.tmx", soundPlayer)); // 59 // 27
        levels.add(Level.loadLevel(assetManager, "levels/camp-fire.tmx", soundPlayer)); // start // 28
        levels.add(Level.loadLevel(assetManager, "levels/bullet-01.tmx", soundPlayer)); // 65 // 31
        levels.add(Level.loadLevel(assetManager, "levels/bullet-02.tmx", soundPlayer)); // 67 // 32
        levels.add(Level.loadLevel(assetManager, "levels/bullet-03.tmx", soundPlayer)); // 69 // 33
        levels.add(Level.loadLevel(assetManager, "levels/bullet-04.tmx", soundPlayer)); // 71 // 34
        levels.add(Level.loadLevel(assetManager, "levels/bullet-05.tmx", soundPlayer)); // 73 // 35
        levels.add(Level.loadLevel(assetManager, "levels/bullet-06.tmx", soundPlayer)); // 75 // 36
        levels.add(Level.loadLevel(assetManager, "levels/bullet-07.tmx", soundPlayer)); // 77 // 37
        levels.add(Level.loadLevel(assetManager, "levels/maze-1.tmx", soundPlayer)); // 79 // 38
        levels.add(Level.loadLevel(assetManager, "levels/enemy-1.tmx", soundPlayer)); // 39
        levels.add(Level.loadLevel(assetManager, "levels/enemy-2.tmx", soundPlayer)); // 40
        levels.add(Level.loadLevel(assetManager, "levels/enemy-3.tmx", soundPlayer)); // 41
        levels.add(Level.loadLevel(assetManager, "levels/enemy-4.tmx", soundPlayer)); // 42
        levels.add(Level.loadLevel(assetManager, "levels/enemy-5.tmx", soundPlayer)); // 43
        levels.add(Level.loadLevel(assetManager, "levels/enemy-6.tmx", soundPlayer)); // 44
        levels.add(Level.loadLevel(assetManager, "levels/enemy-8.tmx", soundPlayer)); // 45
        levels.add(Level.loadLevel(assetManager, "levels/enemy-9.tmx", soundPlayer)); // 46
        levels.add(Level.loadLevel(assetManager, "levels/crossy-road-1.tmx", soundPlayer)); // 48
        levels.add(Level.loadLevel(assetManager, "levels/crossy-road-2.tmx", soundPlayer)); // 49
        levels.add(Level.loadLevel(assetManager, "levels/entrance-2.tmx", soundPlayer)); // 50
        levels.add(Level.loadLevel(assetManager, "levels/stairs-1.tmx", soundPlayer)); // 50
        levels.add(Level.loadLevel(assetManager, "levels/boss-fight.tmx", soundPlayer)); // 50
        levels.add(Level.loadLevel(assetManager, "levels/lobby-2.tmx", soundPlayer)); // 50
        levels.add(Level.loadLevel(assetManager, "levels/gate-1.tmx", soundPlayer)); // 50
        levels.add(Level.loadLevel(assetManager, "levels/gate-2.tmx", soundPlayer)); // 50
        levels.add(Level.loadLevel(assetManager, "levels/gate-3.tmx", soundPlayer)); // 50
        levels.add(Level.loadLevel(assetManager, "levels/ant-catch-up.tmx", soundPlayer)); // 50
        levels.add(Level.loadLevel(assetManager, "levels/trench.tmx", soundPlayer)); // 50
    }

    public int getLevelNumber(Level level) {
        return levels.indexOf(level);
    }

    public Level getLevel(int index) {
        return levels.get(index);
    }

    public Level getLevelFromConnection(String target) {
        for (Level level : levels) {
            if (level.hasConnection(target)) {
                return level;
            }
        }
        return null;
    }

    public Level getLevelFromNumber(String number) {
        for (Level level : levels) {
            if (level.number != null && level.number.equals(number)) {
                return level;
            }
        }
        return null;
    }
}
