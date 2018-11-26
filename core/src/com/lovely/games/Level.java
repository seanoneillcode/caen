package com.lovely.games;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.scene.SceneSource;

import java.util.ArrayList;
import java.util.List;

import static com.lovely.games.Constants.HALF_TILE_SIZE;
import static com.lovely.games.Constants.TILE_SIZE;

public class Level {

    static final int DEFAULT_DELAY = 1;
    final String number;
    boolean isWind = false;

    public List<Connection> connections;
    public List<ArrowSource> arrowSources;
    public List<Block> blocks;
    boolean[][] walls;
    boolean[][] deaths;
    public List<Platform> platforms;
    public String name;
    int numXTiles;
    int numYTiles;
    public List<PressureTile> pressureTiles;
    public List<Door> doors;
    public List<LevelLight> lights;
    public List<DialogSource> dialogSources;
    public List<Torch> torches;
    public List<SceneSource> scenes;
    public Trunk trunk;
    public List<Actor> actors;
    public List<Guff> guffs;
    public List<Wind> winds;
    public List<Enemy> enemies;
    private String music;

    Level(List<Connection> connections, boolean[][] walls, boolean[][] deaths, String name, int numXTiles,
          int numYTiles, List<ArrowSource> arrowSources, List<Platform> platforms, List<Block> blocks,
          List<PressureTile> pressureTiles, List<Door> doors, List<DialogSource> dialogSources,
          List<LevelLight> lights, List<Torch> torches, List<SceneSource> scenes, Trunk trunk, List<Actor> actors,
          List<Wind> winds, List<Enemy> enemies, List<Guff> guffs, boolean isWind, String number, String music) {
        this.connections = connections;
        this.walls = walls;
        this.deaths = deaths;
        this.name = name;
        this.numXTiles = numXTiles;
        this.numYTiles = numYTiles;
        this.arrowSources = arrowSources;
        this.platforms = platforms;
        this.blocks = blocks;
        this.pressureTiles = pressureTiles;
        this.doors = doors;
        this.dialogSources = dialogSources;
        this.lights = lights;
        this.torches = torches;
        this.scenes = scenes;
        this.trunk = trunk;
        this.actors = actors;
        this.winds = winds;
        this.enemies = enemies;
        this.guffs = guffs;
        this.isWind = isWind;
        this.number = number;
        this.music = music;
    }

    Vector2 getConnectionPosition(String name) {
        for (Connection connection : connections) {
            if (connection.name.equals(name)) {
                return connection.pos.cpy();
            }
        }
        return null;
    }

    boolean isWall(Vector2 pos) {
        int tilex = MathUtils.floor(pos.x / TILE_SIZE);
        int tiley = MathUtils.floor(pos.y / TILE_SIZE);

        if (tilex < 0 || tiley < 0 || tilex >= numXTiles || tiley >= numYTiles) {
            return false;
        }
        return walls[tilex][tiley];
    }

    BlockLike getBlockLike(Vector2 pos, boolean noGround) {
        for (BlockLike block : getBlockLikes()) {
            if (!(noGround && block.isGround())) {
                if (pos.dst2(block.getPos().cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE)) < (16 * 16)) {
                    return block;
                }
            }
        }
        return null;
    }

    DialogSource getDialogSource(Vector2 pos) {
        for (DialogSource dialogSource : dialogSources) {
            if (!dialogSource.done && pos.dst2(dialogSource.pos) < 512) {
                return dialogSource;
            }
        }
        return null;
    }

    List<SceneSource> getSceneSources(Vector2 pos) {
        List<SceneSource> sceneSources = new ArrayList<>();
        for (SceneSource sceneSource : scenes) {
            if (!sceneSource.isDone && sceneSource.isActive) {
//                Rectangle playerRect = new Rectangle(pos.x + 1, pos.y + 1, 20, 20);
                if (new Rectangle(sceneSource.pos.x, sceneSource.pos.y, sceneSource.size.x, sceneSource.size.y).contains(pos)) {
                    sceneSource.isDone = true;
                    sceneSources.add(sceneSource);
                }
            }
        }
        return sceneSources;
    }

    Door getDoor(Vector2 pos, boolean isOpen) {
        for (Door door : doors) {
            if (door.isOpen == isOpen) {
                if (pos.dst2(door.pos.cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE)) < 512) {
                    return door;
                }
            }
        }
        return null;
    }

    boolean isLazerBlocked(Vector2 pos) {
        for (BlockLike block : getBlockLikes()) {
            if (!(block.isGround())) {
                if (pos.dst2(block.getPos().cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE)) < (256)) {
                    return true;
                }
            }
        }
        for (Door door : doors) {
            if (!door.isOpen) {
                if (pos.dst2(door.pos.cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE)) < 512) {
                    return true;
                }
            }
        }
        return isWall(pos.cpy());
    }

    boolean isTileBlocked(Vector2 pos) {
        BlockLike block = getBlockLike(pos, true);
        Door door = getDoor(pos, false);
        return isWall(pos) || block != null || door != null;
    }

    public List<BlockLike> getBlockLikes() {
        List<BlockLike> blockLikes = new ArrayList<>();
        blockLikes.addAll(blocks);
        blockLikes.addAll(enemies);
        return blockLikes;
    }

    boolean isDeath(Vector2 pos) {
        int tilex = MathUtils.floor(pos.x / TILE_SIZE);
        int tiley = MathUtils.floor(pos.y / TILE_SIZE);

        if (tilex < 0 || tiley < 0 || tilex >= numXTiles || tiley >= numYTiles) {
            return false;
        }
        return deaths[tilex][tiley];
    }

    boolean isOutOfBounds(Vector2 pos) {
        return pos.x > (numXTiles * TILE_SIZE) || pos.x < 0 || pos.y < 0 || pos.y > (numYTiles * TILE_SIZE);
    }

    List<Platform> getPlatforms() {
        return platforms;
    }

    Platform getPlatform(Vector2 pos) {
        for (Platform platform : platforms) {
            if (pos.dst2(platform.pos) < 512) {
                return platform;
            }
        }
        return null;
    }

    BlockLike getGroundBlock(Vector2 pos) {
        for (BlockLike blockLike : getBlockLikes()) {
            if (blockLike.isGround() && pos.dst2(blockLike.getPos()) < 512) {
                return blockLike;
            }
        }
        return null;
    }

    Connection getConnection(Vector2 pos) {
        for (Connection connection : connections) {
            if(connection.contains(pos)) {
                return connection;
            }
        }
        return null;
    }

    boolean hasConnection(String name) {
        for (Connection connection : connections) {
            if (connection.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    List<ArrowSource> getArrowSources() {
        return this.arrowSources;
    }

    public void resetSceneSources(Vector2 pos) {
        for (SceneSource sceneSource : scenes) {
            if (sceneSource.isDone && sceneSource.isActive && !sceneSource.isPlayOnce()) {
                Rectangle playerRect = new Rectangle(pos.x + 1, pos.y + 1, 20, 20);
                if (!playerRect.overlaps(new Rectangle(sceneSource.pos.x, sceneSource.pos.y, sceneSource.size.x, sceneSource.size.y))) {
                    sceneSource.isDone = false;
                }
            }
        }
    }

    public Wind getWind(Vector2 playerPos) {
        for (Wind wind : winds) {
            Rectangle windRect = new Rectangle(wind.pos.x, wind.pos.y, wind.size.x, wind.size.y);
            if (windRect.contains(playerPos)) {
                return wind;
            }
        }
        return null;
    }

    public Connection getNextConnection() {
        int highest = 0;
        try {
            highest = Integer.valueOf(connections.get(0).to);
        } catch (NumberFormatException e) {
            return null;
        }
        Connection nextConnection = connections.get(0);
        for (Connection connection : connections) {
            if (Integer.valueOf(connection.to) > highest) {
                highest = Integer.valueOf(connection.to);
                nextConnection = connection;
            }
        }
        return nextConnection;
    }

    public Connection getPreviousConnection() {
        int lowest = 0;
        try {
            lowest = Integer.valueOf(connections.get(0).to);
        } catch (NumberFormatException e) {
            return connections.get(0);
        }
        Connection nextConnection = connections.get(0);
        for (Connection connection : connections) {
            if (Integer.valueOf(connection.to) < lowest) {
                lowest = Integer.valueOf(connection.to);
                nextConnection = connection;
            }
        }
        return nextConnection;
    }

    public Connection getConnection(String target) {
        for (Connection connection : connections) {
            if (connection.name.equals(target)) {
                return connection;
            }
        }
        return null;
    }

    public String getMusic() {
        return music;
    }

    /**
     * BUILDEr
     */
    private static class Builder {
        private final boolean isWind;
        private final String number;
        List<Connection> connections = new ArrayList<>();
        List<ArrowSource> arrowSources = new ArrayList<>();
        List<Block> blocks = new ArrayList<>();
        List<PressureTile> pressureTiles = new ArrayList<>();
        boolean[][] walls;
        boolean[][] deaths;
        String name;
        int numXTiles = 0;
        int numYTiles = 0;
        List<Platform> platforms = new ArrayList<>();
        Trunk trunk = new Trunk();
        List<Door> doors = new ArrayList<>();
        List<DialogSource> dialogSources = new ArrayList<>();
        List<LevelLight> lights = new ArrayList<>();
        List<Torch> torches = new ArrayList<>();
        List<SceneSource> scenes = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        private List<Wind> winds = new ArrayList<>();
        List<Enemy> enemies = new ArrayList<>();
        private List<Guff> guffs = new ArrayList<>();
        private String music = null;

        Builder(String name, int numXTiles, int numYTiles, boolean isWind, String number) {
            this.name = name;
            this.numXTiles = numXTiles;
            this.numYTiles = numYTiles;
            this.isWind = isWind;
            this.number = number;
        }

        Builder addConnection(String name, String to, Vector2 pos, Vector2 dir) {
            this.connections.add(new Connection(name, to, pos, dir));
            return this;
        }

        Builder setWalls(boolean[][] walls) {
            this.walls = walls;
            return this;
        }

        Builder setDeaths(boolean[][] deaths) {
            this.deaths = deaths;
            return this;
        }

        Builder addEnemy(Vector2 pos, String dir) {
            this.enemies.add(new Enemy(pos, dir));
            return this;
        }

        Builder addMusic(String music) {
            this.music = music;
            return this;
        }

        Builder addArrowSource(Vector2 dir, Vector2 pos, float offset, float delay, boolean isActive, String switchId, boolean isRandom, float speed, boolean isRed, boolean isHidden) {
            ArrowSource arrowSource = new ArrowSource(pos, dir, offset, delay, isActive, switchId, isRandom, speed, isRed, isHidden);
            if (switchId != null) {
                trunk.addListener(arrowSource);
            }
            this.arrowSources.add(arrowSource);
            return this;
        }

        Builder addPlatform(Platform platform) {
            this.platforms.add(platform);
            if (platform.switchId != null) {
                trunk.addListener(platform);
            }
            return this;
        }

        Builder addBlock(Block block) {
            this.blocks.add(block);
            return this;
        }

        Builder addDoor(Door door) {
            doors.add(door);
            if (door.switchId != null) {
                trunk.addListener(door);
            }
            return this;
        }

        Builder addTorch(Vector2 pos, Color color, boolean isFire, boolean isOn, String switchId) {
            Torch torch = new Torch(pos, color, isFire, switchId, isOn);
            if (switchId != null) {
                trunk.addListener(torch);
            }
            this.torches.add(torch);
            return this;
        }

        Builder addPressureTile(PressureTile pressureTile) {
            this.pressureTiles.add(pressureTile);
            if (pressureTile.switchId != null) {
                pressureTile.setTrunk(trunk);
            }
            return this;
        }

        Builder addDialogSource(DialogSource dialogSource) {
            this.dialogSources.add(dialogSource);
            return this;
        }

        Builder addLight(LevelLight levelLight) {
            if (levelLight.switchId != null) {
                trunk.addListener(levelLight);
            }
            lights.add(levelLight);
            return this;
        }

        Builder addActor(Vector2 pos, String id, boolean isHide, boolean isRight, boolean isBoss) {
            actors.add(new Actor(pos, id, isHide, isRight, isBoss));
            return this;
        }

        Builder addScene(String id, Vector2 pos, Vector2 size, boolean playOnce, boolean isActive, String switchId) {
            SceneSource sceneSource = new SceneSource(pos, id, size, playOnce, isActive, switchId);
            if (switchId != null) {
                trunk.addListener(sceneSource);
            }
            this.scenes.add(sceneSource);
            return this;
        }

        Builder addWind(Vector2 pos, Vector2 size, String dir) {
            this.winds.add(new Wind(pos, size, dir));
            return this;
        }

        Builder addGuff(Vector2 pos, Vector2 size, String image, boolean isOnTop, boolean hide) {
            this.guffs.add(new Guff(image, pos, size, isOnTop, hide));
            return this;
        }

        Trunk getTrunk() {
            return this.trunk;
        }

        Level build() {
            if (walls == null) {
                walls = new boolean[numXTiles][numYTiles];
            }
            if (deaths == null) {
                deaths = new boolean[numXTiles][numYTiles];
            }
            return new Level(connections, walls, deaths, name, numXTiles, numYTiles, arrowSources, platforms, blocks,
                    pressureTiles, doors, dialogSources, lights, torches, scenes, trunk, actors, winds, enemies, guffs, isWind, number, music);
        }


    }

    static Level loadLevel(AssetManager assetManager, String name, SoundPlayer soundPlayer) {
        TiledMap tiledMap = assetManager.get(name);
        MapProperties mapProperties = tiledMap.getProperties();
        int levelWidth = (Integer) mapProperties.get("width");
        int levelHeight = (Integer) mapProperties.get("height");
        boolean isWind = mapProperties.containsKey("isWind") && Boolean.valueOf((String) mapProperties.get("isWind"));
        String levelNumber = (String) mapProperties.get("number");
        Builder builder = new Builder(name, levelWidth, levelHeight, isWind, levelNumber);
        builder.addMusic((String) mapProperties.get("music"));

        MapLayer objectLayer = tiledMap.getLayers().get("objects");
        MapObjects mapObjects = objectLayer.getObjects();

        // connections
        for (MapObject obj : mapObjects) {
            MapProperties properties = obj.getProperties();
            if (properties.containsKey("type") && properties.get("type").equals("connection")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                String objName = obj.getName();
                String to = null;
                if (properties.containsKey("to")) {
                    to = properties.get("to").toString();
                }
                Vector2 dir = new Vector2();
                if (properties.containsKey("dir")) {
                    String dirString = properties.get("dir").toString();
                    if (dirString.equals("up")) {
                        dir = new Vector2(0, 1);
                    }
                    if (dirString.equals("down")) {
                        dir = new Vector2(0, -1);
                    }
                    if (dirString.equals("left")) {
                        dir = new Vector2(-1, 0);
                    }
                    if (dirString.equals("right")) {
                        dir = new Vector2(1, 0);
                    }
                }
                builder.addConnection(objName, to, new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y), dir);
            }
            if (properties.containsKey("type") && properties.get("type").equals("arrowSource")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 dir = new Vector2(0, 0);
                float offset = 0;
                float delay = DEFAULT_DELAY;
                float speed = 1f;
                boolean isRandom = false;
                if (properties.containsKey("xdir")) {
                    dir.x = Integer.parseInt(properties.get("xdir").toString());
                }
                if (properties.containsKey("ydir")) {
                    dir.y = Integer.parseInt(properties.get("ydir").toString());
                }
                if (properties.containsKey("offset")) {
                    offset = Float.parseFloat(properties.get("offset").toString());
                }
                if (properties.containsKey("delay")) {
                    delay = Float.parseFloat(properties.get("delay").toString());
                }
                if (properties.containsKey("speed")) {
                    speed = Float.parseFloat(properties.get("speed").toString());
                }
                if (properties.containsKey("isRandom")) {
                    isRandom = Boolean.parseBoolean(properties.get("isRandom").toString());
                }
                boolean isRed = false;
                if (properties.containsKey("isRed")) {
                    isRed = Boolean.parseBoolean(properties.get("isRed").toString());
                }
                boolean isHidden = false;
                if (properties.containsKey("isHidden")) {
                    isHidden = Boolean.parseBoolean(properties.get("isHidden").toString());
                }
                boolean isActive = true;
                if (properties.containsKey("isActive")) {
                    isActive = Boolean.parseBoolean(properties.get("isActive").toString());
                }
                String switchId = null;
                if (properties.containsKey("switch")) {
                    switchId = properties.get("switch").toString();
                }
                Vector2 midPos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                builder.addArrowSource(dir, midPos, offset, delay, isActive, switchId, isRandom, speed, isRed, isHidden);
            }
            if (properties.containsKey("type") && properties.get("type").equals("platform")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 start = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                Vector2 end = start.cpy();
                float offset = 0;
                boolean isActive = true;
                if (properties.containsKey("movex")) {
                    end.x = end.x + (TILE_SIZE * Float.parseFloat(properties.get("movex").toString()));
                }
                if (properties.containsKey("movey")) {
                    end.y = end.y + (TILE_SIZE * Float.parseFloat(properties.get("movey").toString()));
                }
                if (properties.containsKey("offset")) {
                    offset = Float.parseFloat(properties.get("offset").toString());
                }
                if (properties.containsKey("isActive")) {
                    isActive = Boolean.parseBoolean(properties.get("isActive").toString());
                }
                String switchId = null;
                if (properties.containsKey("switch")) {
                    switchId = properties.get("switch").toString();
                }
                Platform platform = new Platform(start, end, offset, isActive, switchId);
                builder.addPlatform(platform);
            }
            if (properties.containsKey("type") && properties.get("type").equals("enemy")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                String dir = "down";
                if (properties.containsKey("dir")) {
                    dir = properties.get("dir").toString();
                }
                builder.addEnemy(pos, dir);
            }
            if (properties.containsKey("type") && properties.get("type").equals("block")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                builder.addBlock(new Block(pos));
            }
            if (properties.containsKey("type") && properties.get("type").equals("torch")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                boolean isFire = false;
                if (properties.containsKey("isFire")) {
                    isFire = Boolean.parseBoolean(properties.get("isFire").toString());
                }
                Color color = new Color(1.0f,0.75f,0.25f,1.0f);
                if (properties.containsKey("r")) {
                    float r = Float.valueOf(properties.get("r").toString());
                    float g = Float.valueOf(properties.get("g").toString());
                    float b = Float.valueOf(properties.get("b").toString());
                    float a = Float.valueOf(properties.get("a").toString());
                    color = new Color(r, g, b, a);
                }
                String switchId = null;
                if (properties.containsKey("switch")) {
                    switchId = properties.get("switch").toString();
                }
                boolean isOn = true;
                if (properties.containsKey("isOn")) {
                    isOn = Boolean.parseBoolean(properties.get("isOn").toString());
                }
                builder.addTorch(pos, color, isFire, isOn, switchId);
            }
            if (properties.containsKey("type") && properties.get("type").equals("actor")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                boolean isHide = false;
                if (properties.containsKey("isHide")) {
                    isHide = Boolean.parseBoolean(properties.get("isHide").toString());
                }
                boolean isRight = false;
                if (properties.containsKey("isRight")) {
                    isRight = Boolean.parseBoolean(properties.get("isRight").toString());
                }
                boolean isBoss = false;
                if (properties.containsKey("isBoss")) {
                    isBoss = Boolean.parseBoolean(properties.get("isBoss").toString());
                }
                builder.addActor(pos, rectObj.getName(), isHide, isRight, isBoss);
            }
            if (properties.containsKey("type") && properties.get("type").equals("door")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                String switchId = null;
                if (properties.containsKey("switch")) {
                    switchId = properties.get("switch").toString();
                }
                boolean isOpen = false;
                if (properties.containsKey("isOpen")) {
                    isOpen = Boolean.parseBoolean(properties.get("isOpen").toString());
                }
                boolean across = false;
                if (properties.containsKey("across")) {
                    across = Boolean.parseBoolean(properties.get("across").toString());
                }
                builder.addDoor(new Door(pos, isOpen, switchId, soundPlayer, across));
            }
            if (properties.containsKey("type") && properties.get("type").equals("light")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                Vector2 size = new Vector2(rectObj.getRectangle().width, rectObj.getRectangle().height);

                Color color = null;
                if (properties.containsKey("r")) {
                    float r = Float.valueOf(properties.get("r").toString());
                    float g = Float.valueOf(properties.get("g").toString());
                    float b = Float.valueOf(properties.get("b").toString());
                    float a = Float.valueOf(properties.get("a").toString());
                    color = new Color(r, g, b, a);
                }
                String switchId = null;
                if (properties.containsKey("switch")) {
                    switchId = properties.get("switch").toString();
                }
                builder.addLight(new LevelLight(pos, size, color, switchId));
            }
            if (properties.containsKey("type") && properties.get("type").equals("guff")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                Vector2 size = new Vector2(rectObj.getRectangle().width, rectObj.getRectangle().height);
                String image = properties.get("image").toString();
                boolean isOnTop = false;
                if (properties.containsKey("isOnTop")) {
                    isOnTop = Boolean.parseBoolean(properties.get("isOnTop").toString());
                }
                boolean hide = false;
                if (properties.containsKey("hide")) {
                    hide = Boolean.parseBoolean(properties.get("hide").toString());
                }
                builder.addGuff(pos, size, image, isOnTop, hide);
            }
            if (properties.containsKey("type") && properties.get("type").equals("scene")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                Vector2 size = new Vector2(rectObj.getRectangle().width, rectObj.getRectangle().height);
                boolean playOnce = true;
                boolean isActive = true;
                if (properties.containsKey("playOnce")) {
                    playOnce = Boolean.parseBoolean(properties.get("playOnce").toString());
                }
                if (properties.containsKey("isActive")) {
                    isActive = Boolean.parseBoolean(properties.get("isActive").toString());
                }
                String switchId = null;
                if (properties.containsKey("switch")) {
                    switchId = properties.get("switch").toString();
                }
                builder.addScene(obj.getName(), pos, size, false, isActive, switchId);
            }
            if (properties.containsKey("type") && properties.get("type").equals("pressure")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                String switchId = null;
                if (properties.containsKey("switch")) {
                    switchId = properties.get("switch").toString();
                }
                boolean isSwitch = false;
                if (properties.containsKey("isSwitch")) {
                    isSwitch = Boolean.parseBoolean(properties.get("isSwitch").toString());
                }
                builder.addPressureTile(new PressureTile(pos, switchId, isSwitch));
            }
            if (properties.containsKey("type") && properties.get("type").equals("wind")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                Vector2 size = new Vector2(rectObj.getRectangle().width, rectObj.getRectangle().height);
                String dir = "down";
                if (properties.containsKey("dir")) {
                    dir = properties.get("dir").toString();
                }
                builder.addWind(pos, size, dir);
            }
            if (properties.containsKey("type") && properties.get("type").equals("dialog")) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Vector2 pos = new Vector2(rectObj.getRectangle().x, rectObj.getRectangle().y);
                String id = obj.getName();
                builder.addDialogSource(new DialogSource(pos, id));
            }
        }

        // walls
        boolean[][] walls = new boolean[levelWidth][levelHeight];
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("walls");
        for (int y = 0; y < tiledLayer.getHeight(); y++) {
            for (int x = 0; x < tiledLayer.getWidth(); x++) {
                walls[x][y] = tiledLayer.getCell(x,y) != null;
            }
        }
        builder.setWalls(walls);

        // deaths
        boolean[][] deaths = new boolean[levelWidth][levelHeight];
        tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("death");
        if (tiledLayer != null) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                for (int x = 0; x < tiledLayer.getWidth(); x++) {
                    deaths[x][y] = tiledLayer.getCell(x,y) != null;
                }
            }
            builder.setDeaths(deaths);
        }

        return builder.build();
    }
}
