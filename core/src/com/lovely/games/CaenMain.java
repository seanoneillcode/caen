package com.lovely.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.lovely.games.dialog.Conversation;
import com.lovely.games.dialog.DialogContainer;
import com.lovely.games.dialog.DialogSource;
import com.lovely.games.entity.Arrow;
import com.lovely.games.entity.ArrowSource;
import com.lovely.games.entity.Block;
import com.lovely.games.entity.BlockLike;
import com.lovely.games.entity.Door;
import com.lovely.games.entity.Enemy;
import com.lovely.games.entity.Explosion;
import com.lovely.games.entity.Guff;
import com.lovely.games.entity.Platform;
import com.lovely.games.entity.PressureTile;
import com.lovely.games.entity.Torch;
import com.lovely.games.render.LightRenderer;
import com.lovely.games.render.MenuRenderer;
import com.lovely.games.scene.verbs.DialogVerb;
import com.lovely.games.scene.NewGameScene;
import com.lovely.games.scene.Scene;
import com.lovely.games.scene.SceneContainer;
import com.lovely.games.scene.SceneSource;
import com.lovely.games.scene.StonePrizeScene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.lovely.games.Constants.*;

public class CaenMain extends ApplicationAdapter implements Stage {

    private Vector2 playerSceneMovement = new Vector2();
    private Vector2 playerPos, playerDir;
    private Vector2 moveVector;
    private Vector2 inputVector;
    private Vector2 cameraTargetPos;
    private Vector2 playerMovement = new Vector2();
    private SpriteBatch batch;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Texture lazerImage, horizontalLazerImage;
    private String currentSpell;
    private String posterImageName;
    private String lastConnectionNumber = "";
    private String antAnim = "normal";
    private Color fadeColor = Color.BLACK;
    private float stepTimer = 0;
    private float lazerSoundTimer = 0;
    private float animationDelta = 0;
    private float antAnimDelta = 0;
    private float walkAnimDelta = 0;
    private float castCooldown = 0;
    private float posterAlpha;
    private float screenFade;
    public float gamma;
    private float playerDeathTimer = 0;
    private float playerShootingTimer = 0;
    private float levelTransitionTimer = 0;
    private float loadLevelTimer = 0;
    private boolean dialogLock = false;
    private boolean isLevelDirty = false;
    private boolean moveLock, snaplock;
    private boolean skipLock;
    private boolean castLock;
    private boolean staticLevel;
    private boolean levelChangeLock = false;
    private boolean isWalkOne = true;
    private boolean wasMoving;
    private boolean playerFacingLeft = false;
    private boolean playerIsPushing = false;
    private boolean playerWasPushing = false;
    private boolean playerIsDead = false;
    private boolean isFallDeath = false;
    private boolean isPlayerShooting = false;
    private boolean isViewDirty = false;
    private boolean isPaused = false;
    private boolean leaveLevel = false;
    private boolean isHidePlayer;
    public boolean isPlayingOpeningScene;
    private int pid;
    private int bestLevelSoFar = 0;
//    private Strng lastMusic = "none";

    protected Level currentLevel;
    private Connection lastConnection;
    private Platform currentPlatform;
    private SceneContainer sceneContainer;
    private DialogVerb activeDialogVerb;
    private DialogContainer dialogContainer;
    private Conversation conversation;
    private com.lovely.games.scene.StonePrizeScene stonePrizeScene = null;
    private com.lovely.games.scene.NewGameScene newGameScene = null;
    private BlockLike currentImageHeight = null;
    private ScreenFader screenFader;
    private Level nextLevel = null;
    private Connection nextConnection = null;
    private Platform lockedPlatform;
    private BlockLike lastBlock;

    private List<MyEffect> effects;
    private List<Explosion> explosions;
    private List<Arrow> arrows;
    private List<Scene> currentScenes;

    public SoundPlayer soundPlayer;
    public MyInputProcessor inputProcessor;
    private StatisticsManager statisticsManager;
    private AssetManager assetManager;
    private LevelManager levelManager;
    private SpriteManager spriteManager;
    private AnimationManager animationManager;
    private MenuRenderer menuRenderer;
    private Menu menu;
    private LightRenderer lightRenderer;
    private float sceneTargetZoom = 0;

    @Override
	public void create () {
        AssetLoader assetLoader = new AssetLoader();
        assetManager = assetLoader.getLoadedAssetManager();

        dialogContainer = new DialogContainer(assetManager);
        inputVector = new Vector2();
        statisticsManager = new StatisticsManager(pid);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        inputProcessor = new MyInputProcessor();

        batch = new SpriteBatch();

        explosions = new ArrayList<>();
        soundPlayer = new SoundPlayer(assetManager);
        levelManager = new LevelManager(assetManager, soundPlayer);


        lazerImage = assetManager.get("entity/lazer.png");
        horizontalLazerImage = assetManager.get("entity/lazer-horizontal.png");

        spriteManager = new SpriteManager(assetManager);
        spriteManager.getSprite("posterSprite").setBounds(0,0,VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        spriteManager.getSprite("arrowSprite").setBounds(0,0,32,32);

        screenFader = new ScreenFader();
        screenFade = 0f;
        gamma = 0.2f;

        animationManager = new AnimationManager();
        animationManager.load(assetManager);

        menu = new Menu();
        menuRenderer = new MenuRenderer(menu, spriteManager, animationManager);

        currentScenes = new ArrayList<>();
        currentSpell = "";

        effects = new ArrayList<>();
        stonePrizeScene = new StonePrizeScene(assetManager);
        newGameScene = new NewGameScene(animationManager.openingScene);

        moveLock = false;
        lightRenderer = new LightRenderer(animationManager, spriteManager);

        sceneContainer = new SceneContainer();
        fadeScreen(true, 2.0f, Color.BLACK);

        // special
//        startLevel(startLevel, startLevel.getPreviousConnection());
//        Gdx.app.getPreferences("caen-preferences").clear();

        loadEverything();
        loadLevelFromPrefs();
        menu.isTitleMenu = true;
        isViewDirty = true;
        menu.titleLock = true;
	}

    @Override
    public void resize(int width, int height) {

    }

    public void saveEverything() {
        saveEverything(levelManager.getLevelNumber(currentLevel), lastConnectionNumber);
    }

    private void saveEverything(int levelNumber, String lastConnectionNumber) {
        Preferences prefs = Gdx.app.getPreferences("caen-preferences");
        prefs.putInteger("last-level", levelNumber);
        prefs.putString("last-connection-number", lastConnectionNumber);
        prefs.putFloat("sound-level", soundPlayer.getSoundVolume());
        prefs.putFloat("music-level", soundPlayer.getMusicVolume());
        prefs.putFloat("brightness-level", gamma);
        prefs.putString("current-spell", currentSpell);
        prefs.putString("last-played-song", soundPlayer.getLastPlayedSong());
        prefs.putInteger("pid", pid);
        inputProcessor.keyMappings.forEach(prefs::putInteger);
        prefs.flush();
        menu.setHasContinue(true);
    }

    private int generatePid() {
        return String.valueOf(System.currentTimeMillis()).hashCode();
    }

    public void loadEverything() {
        Preferences prefs = Gdx.app.getPreferences("caen-preferences");
        soundPlayer.setSoundVolume(prefs.getFloat("sound-level", DEFAULT_SOUND_LEVEL));
        soundPlayer.setMusicVolume(prefs.getFloat("music-level", DEFAULT_MUSIC_LEVEL));
        soundPlayer.setLastPlayedSong(prefs.getString("last-played-song", "none"));
        gamma = prefs.getFloat("brightness-level", DEFAULT_GAMMA);
        inputProcessor.keyMappings.put("up key", prefs.getInteger("up key", 19));
        inputProcessor.keyMappings.put("right key", prefs.getInteger("right key", 22));
        inputProcessor.keyMappings.put("down key", prefs.getInteger("down key", 20));
        inputProcessor.keyMappings.put("left key", prefs.getInteger("left key", 21));
        inputProcessor.keyMappings.put("cast key", prefs.getInteger("cast key", 62));
        pid = prefs.getInteger("pid", generatePid());
        statisticsManager.pid = pid;
        if (prefs.contains("current-spell")) {
            currentSpell = prefs.getString("current-spell");
        } else {
            currentSpell = "";
        }
        if (prefs.contains("last-level")) {
            menu.setHasContinue(prefs.getInteger("last-level") != START_LEVEL_NUM);
        } else {
            menu.setHasContinue(false);
        }
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    private void loadLevelFromPrefs() {
        Preferences prefs = Gdx.app.getPreferences("caen-preferences");
        int lastLevel;
        if (prefs.contains("last-level")) {
            lastLevel = prefs.getInteger("last-level");
        } else {
            lastLevel = START_LEVEL_NUM;
        }
        Level level = levelManager.getLevel(lastLevel);
        Connection connection = level.getPreviousConnection();
        if (prefs.contains("last-connection-number")) {
            lastConnectionNumber = prefs.getString("last-connection-number");
            if (level.hasConnection(lastConnectionNumber)) {
                connection = level.getConnection(lastConnectionNumber);
            }
        }

        startLevel(level, connection);
    }

    private void loadLevel(Level level) {
        TiledMap map = assetManager.get(level.name);
        mapRenderer = new OrthogonalTiledMapRenderer(map, batch);
        mapRenderer.setView(camera);
    }

    private void startLevel(Level level, Connection startConnection) {
        currentPlatform = null;
        effects.clear();
        playerIsDead = false;
        playerDeathTimer = 0;
        stepTimer = -1;
        loadLevel(level);
        currentLevel = level;
        saveEverything(levelManager.getLevelNumber(currentLevel), startConnection.name);
        playerPos = startConnection.pos.cpy();
        inputVector = new Vector2();
        moveVector = new Vector2();
        arrows = new ArrayList<>();
        lastConnection = startConnection;
        explosions = new ArrayList<>();
        for (ArrowSource arrowSource : currentLevel.getArrowSources()) {
            arrowSource.start();
        }
        for (Platform platform : currentLevel.getPlatforms()) {
            platform.start(soundPlayer);
        }
        for (Block block : currentLevel.blocks) {
            block.start();
        }
        for (Enemy enemy : currentLevel.enemies) {
            enemy.start();
        }
        for (Connection connection : currentLevel.connections) {
            connection.reset();
        }
        for (PressureTile pressureTile : currentLevel.pressureTiles) {
            pressureTile.start();
        }
        for (Actor actor : currentLevel.actors) {
            actor.start();
        }
        for (Door door : currentLevel.doors) {
            door.start();
        }
        for (Torch torch : currentLevel.torches) {
            torch.start();

        }
        for (Scene scene : currentScenes) {
            scene.start();
        }
        currentScenes.clear();
        for (SceneSource sceneSource : currentLevel.scenes) {
            sceneSource.start();
            sceneContainer.scenes.get(sceneSource.id).start();
        }
        isLevelDirty = true;
        moveLock = false;
        cameraTargetPos = null;
        playerDir = new Vector2(1,0);
        if (level.name.equals("levels/camp-fire.tmx") || level.name.equals("levels/trailer-level.tmx")) {
            staticLevel = true;
        } else {
            staticLevel = false;
        }
        if (level.name.equals("levels/lobby-2.tmx")) {
            staticLevel = true;
        }
        nextLevel = null;
        nextConnection = null;
        levelTransitionTimer = LEVEL_TRANSITION_TIMER;
        leaveLevel = false;
        stonePrizeScene.reset();
        soundPlayer.startLevel();
        if (currentLevel.getMusic() != null) {
            soundPlayer.levelMusic(currentLevel.getMusic());
        }
        if (currentLevel.number != null && Integer.valueOf(currentLevel.number) > bestLevelSoFar) {
            bestLevelSoFar = Integer.valueOf(currentLevel.number);
            statisticsManager.addGameEvent(statisticsManager.startLevelEvent(level));
        }
        antAnim = "normal";
        for (Guff guff : currentLevel.guffs) {
            guff.reset();
        }
        soundPlayer.resumeMusic();
    }

    private Vector3 getCameraPosition() {
        Vector2 pos = cameraTargetPos == null ? playerPos : cameraTargetPos;
        if (staticLevel) {
            pos = new Vector2(280, 240);
        }
        if (menu.shouldHandleMenu()) {
            return new Vector3(280, 240, 0);
        }
        Vector3 target = new Vector3(pos.x, pos.y, 0);
        if (currentLevel.name.equals("levels/tower-bridge-1.tmx")) {
            camera.zoom = 0.9f;
        }
        final float speed = CAMERA_CATCHUP_SPEED * Gdx.graphics.getDeltaTime();
        float ispeed = 1.0f - speed;
        Vector3 cameraPosition = camera.position.cpy();
        cameraPosition.scl(ispeed);
        target.scl(speed);
        cameraPosition.add(target);
        if (Math.abs(cameraPosition.x - pos.x) < CAMERA_MARGIN) {
            cameraPosition.x = pos.x;
        }
        if (Math.abs(cameraPosition.y - pos.y) < CAMERA_MARGIN) {
            cameraPosition.y = pos.y;
        }
        if (snaplock && pos.dst2(new Vector2(cameraPosition.x, cameraPosition.y)) < 10000) {
            snaplock = false;
        }
        if (!snaplock) {
            float cameraTrailLimit = 100f;
            cameraPosition.x = MathUtils.clamp(cameraPosition.x, -cameraTrailLimit + pos.x, cameraTrailLimit + pos.x);
            cameraPosition.y = MathUtils.clamp(cameraPosition.y, -cameraTrailLimit + pos.y, cameraTrailLimit + pos.y);
        }

        return cameraPosition;
    }

    private boolean bossIsFighting() {
        for (Actor actor : currentLevel.actors) {
            if (actor.isBoss) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render () {
        if (isPaused) {
            return;
        }
        updateCameraZoom();
        camera.position.set(getCameraPosition());
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        if ((!menu.shouldHandleMenu() || isBrightnessOption()) && !isPlayingOpeningScene) {
            mapRenderer.setView(camera);
            update();
        }
        getInput();
        animationDelta = animationDelta + Gdx.graphics.getDeltaTime();
        if ((!menu.shouldHandleMenu() || isBrightnessOption()) && !isPlayingOpeningScene) {
            lightRenderer.render(currentLevel, animationDelta, this);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (loadLevelTimer > 0.45f) {
            return;
        }

        if (!isLevelDirty && (!menu.shouldHandleMenu() || isBrightnessOption()) && !isViewDirty && !isPlayingOpeningScene) {
            Vector2 threeDeeLinePos = playerPos.cpy().add(0, 0);
            mapRenderer.render();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            for (Guff guff : currentLevel.guffs) {
                if (!guff.isOnTop() && !guff.hide) {
                    TextureRegion currentFrame = animationManager.getGuff(guff.imageName).getKeyFrame(animationDelta + guff.offset, true);
                    batch.draw(currentFrame, guff.pos.x, guff.pos.y, guff.size.x, guff.size.y);
                }
            }
            for (PressureTile pressureTile : currentLevel.pressureTiles) {
                TextureRegion frame;
                if (pressureTile.isSwitch) {
                    Animation<TextureRegion> animation = pressureTile.isPressure ? animationManager.switchOnAnim : animationManager.switchOffAnim;
                    frame = animation.getKeyFrame(pressureTile.animTimer, false);
                } else {
                    Animation<TextureRegion> animation = pressureTile.isPressure ? animationManager.pressureOnAnim : animationManager.pressureOffAnim;
                    frame = animation.getKeyFrame(pressureTile.animTimer, false);
                }
                batch.draw(frame, pressureTile.pos.x, pressureTile.pos.y);
            }
            for (Platform platform : currentLevel.getPlatforms()) {
                TextureRegion frame = animationManager.platformAnim.getKeyFrame(platform.getAnimTimer(), true);
                float height = 0;
                batch.draw(frame, platform.pos.x, platform.pos.y + height);
            }
            for (MyEffect effect : effects) {
                TextureRegion currentFrame = animationManager.getGuff(effect.name).getKeyFrame(effect.timer, true);
                batch.draw(currentFrame, effect.pos.x, effect.pos.y);
            }
            List<BlockLike> blockLikes = currentLevel.getBlockLikes();
            blockLikes.sort((o1, o2) -> (int)(o2.getPos().y - o1.getPos().y));
            Sprite blockSprite = spriteManager.getSprite("blockSprite");
            Sprite groundBlockSprite = spriteManager.getSprite("groundBlockSprite");
            for (BlockLike blockLike : blockLikes) {
                float height = (MathUtils.sinDeg(blockLike.getAnimTimer() * 360) * 2f);
                if (!(blockLike instanceof Enemy)) {
                    if (blockLike.isGround()) {
                        Animation<TextureRegion> animation = animationManager.groundBlockAnim;
                        groundBlockSprite.setRegion(animation.getKeyFrame(blockLike.getAnimTimer(), true));
                        groundBlockSprite.setPosition(blockLike.getPos().x, blockLike.getPos().y - height);
                        groundBlockSprite.draw(batch);
                    }
                    if (blockLike.getPos().y > threeDeeLinePos.y && !blockLike.isGround()) {
                        blockSprite.setPosition(blockLike.getPos().x, blockLike.getPos().y);
                        blockSprite.draw(batch);
                    }
                }
                if (blockLike instanceof Enemy) {
                    if (blockLike.getPos().y > threeDeeLinePos.y) {
                        drawEnemy((Enemy) blockLike, height);
                    }
                }
            }
            Sprite arrowSourceSprite = spriteManager.getSprite("arrowSourceSprite");
            for (ArrowSource arrowSource : currentLevel.arrowSources) {
                if (arrowSource.isHidden) {
                    continue;
                }
                TextureRegion frame = animationManager.arrowSourceAnim.getKeyFrame(arrowSource.getAnimTimer(), true);
                arrowSourceSprite.setPosition(arrowSource.pos.x, arrowSource.pos.y - 16);
                arrowSourceSprite.setRegion(frame);
                arrowSourceSprite.setRotation(0);
                if (arrowSource.dir.x < 0) {
                    arrowSourceSprite.setRotation(270);
                    arrowSourceSprite.setPosition(arrowSource.pos.x - 68, arrowSource.pos.y - 16 - 24);
                }
                if (arrowSource.dir.x > 0) {
                    arrowSourceSprite.setRotation(90);
                    arrowSourceSprite.setPosition(arrowSource.pos.x - 28, arrowSource.pos.y - 16 + 72);
                }
                if (arrowSource.dir.y > 0) {
                    arrowSourceSprite.setRotation(180);
                    arrowSourceSprite.setPosition(arrowSource.pos.x - 94, arrowSource.pos.y - 16 + 40);
                }
                arrowSourceSprite.draw(batch);
            }
            Sprite arrowSprite = spriteManager.getSprite("arrowSprite");
            for (Arrow arrow : arrows) {
                if (arrow.isArrow) {
                    TextureRegion currentFrame = animationManager.arrowAnim.getKeyFrame(animationDelta, true);
                    arrowSprite.setPosition(arrow.pos.x, arrow.pos.y + 12);
                    arrowSprite.setRegion(currentFrame);
                    if (arrow.isRed) {
                        arrowSprite.setColor(new Color(1f, 0.1f, 0.1f, 1f));
                    } else {
                        arrowSprite.setColor(Color.WHITE);
                    }
                    arrowSprite.draw(batch);
                } else {
                    Texture img = arrow.dir.x != 0 ? horizontalLazerImage : lazerImage;
                    batch.draw(img, arrow.pos.x, arrow.pos.y);
                }
            }
            Sprite doorSprite = spriteManager.getSprite("doorSprite");
            for (Door door : currentLevel.doors) {
                Animation<TextureRegion> animation;
                if (door.isAcross) {
                    animation = door.isOpen ? animationManager.doorAcrossOpenAnim : animationManager.doorAcrossCloseAnim;
                } else {
                    animation = door.isOpen ? animationManager.doorOpenAnim : animationManager.doorCloseAnim;
                }
                TextureRegion frame = animation.getKeyFrame(door.animTimer, false);
                TextureRegion dustFrame = animationManager.doorDustAnim.getKeyFrame(door.animTimer, false);
                doorSprite.setRegion(frame);
                doorSprite.setPosition(door.pos.x, door.pos.y);
                if (door.isOpen) {
                    doorSprite.draw(batch);
                    doorSprite.setRegion(dustFrame);
                    doorSprite.draw(batch);
                }
                if (!door.isOpen && door.pos.y >= threeDeeLinePos.y) {
                    doorSprite.draw(batch);
                    doorSprite.setRegion(dustFrame);
                    doorSprite.draw(batch);
                }
            }
            for (Actor actor : currentLevel.actors) {
                if (!actor.isHidden && actor.pos.y >= threeDeeLinePos.y) {
                    drawActor(actor);
                }
            }
            for (Torch torch : currentLevel.torches) {
                if (torch.pos.y >= threeDeeLinePos.y && torch.isOn) {
                    if (torch.isFire) {
                        TextureRegion torchFrame = animationManager.campfireAnim.getKeyFrame(animationDelta, true);
                        batch.draw(torchFrame, torch.pos.x - 20, torch.pos.y - 10);
                    } else {
                        TextureRegion torchFrame = animationManager.torchAnim.getKeyFrame(animationDelta, true);
                        batch.draw(torchFrame, torch.pos.x, torch.pos.y);
                    }
                }
            }
            float heightAdjustment = 0f;
            if (currentImageHeight != null) {
                float height = (MathUtils.sinDeg(currentImageHeight.getAnimTimer() * 360) * 2f);
                heightAdjustment = height + 2f;
            }
            Sprite playerSprite = spriteManager.getSprite("playerSprite");
            playerSprite.setPosition(playerPos.x, playerPos.y + HALF_TILE_SIZE  - heightAdjustment );
            TextureRegion currentFrame;
            if (!playerIsDead && (levelTransitionTimer > 0 || isMoving())) {
                if (playerIsPushing || playerWasPushing) {
                    currentFrame = animationManager.pushBlock.getKeyFrame(walkAnimDelta, true);
                } else {

                    currentFrame = animationManager.walkRight.getKeyFrame(walkAnimDelta, true);

                    if (playerDir.y > 0) {
                        currentFrame = animationManager.walkUp.getKeyFrame(walkAnimDelta, true);
                    }
                    if (playerDir.y < 0) {
                        currentFrame = animationManager.walkDown.getKeyFrame(walkAnimDelta, true);
                    }
                }
            } else {
                if (playerIsDead) {
                    if (isFallDeath) {
                        currentFrame = animationManager.fallDeath.getKeyFrame(animationDelta, false);
                    } else {
                        currentFrame = animationManager.fireDeath.getKeyFrame(animationDelta, false);
                    }
                } else {
                    if (isPlayerShooting && currentSpell != null && !currentSpell.equals("")) {
                        currentFrame = animationManager.playerShoot.getKeyFrame(animationDelta, true);
                    } else {
                        currentFrame = animationManager.idleAnim.getKeyFrame(animationDelta, true);
                    }
                }
            }
            playerSprite.setRegion(currentFrame);
            if (playerFacingLeft) {
                playerSprite.flip(true, false);
            }
            if (!isHidePlayer) {
                if (!playerIsDead) {
                    batch.draw(assetManager.get("character/player-shadow.png",Texture.class), playerPos.x, playerPos.y - heightAdjustment);
                }
                playerSprite.draw(batch);
            }
            for (Guff guff : currentLevel.guffs) {
                if (guff.isOnTop() && !guff.hide) {
                    currentFrame = animationManager.getGuff(guff.imageName).getKeyFrame(animationDelta + guff.offset, true);
                    batch.draw(currentFrame, guff.pos.x, guff.pos.y, guff.size.x, guff.size.y);
                }
            }
            for (BlockLike blockLike : blockLikes) {
                if (!(blockLike instanceof Enemy)) {
                    if (blockLike.getPos().y <= threeDeeLinePos.y && !blockLike.isGround()) {
                        blockSprite.setPosition(blockLike.getPos().x, blockLike.getPos().y);
                        blockSprite.draw(batch);
                    }
                }
                if (blockLike instanceof Enemy) {
                    if (blockLike.getPos().y <= threeDeeLinePos.y && !blockLike.isGround()) {
                        drawEnemy((Enemy) blockLike, 0);
                    }
                }
            }
            for (Door door : currentLevel.doors) {
                Animation<TextureRegion> animation;
                if (door.isAcross) {
                    animation = door.isOpen ? animationManager.doorAcrossOpenAnim : animationManager.doorAcrossCloseAnim;
                } else {
                    animation = door.isOpen ? animationManager.doorOpenAnim : animationManager.doorCloseAnim;
                }
                TextureRegion dustFrame = animationManager.doorDustAnim.getKeyFrame(door.animTimer, false);
                TextureRegion frame = animation.getKeyFrame(door.animTimer, false);
                doorSprite.setRegion(frame);
                doorSprite.setPosition(door.pos.x, door.pos.y);
                if (door.pos.y < threeDeeLinePos.y && !door.isOpen) {
                    doorSprite.draw(batch);
                    doorSprite.setRegion(dustFrame);
                    doorSprite.draw(batch);
                }
            }
            for (Torch torch : currentLevel.torches) {
                if (torch.pos.y < threeDeeLinePos.y && torch.isOn) {
                    if (torch.isFire) {
                        TextureRegion torchFrame = animationManager.campfireAnim.getKeyFrame(animationDelta, true);
                        batch.draw(torchFrame, torch.pos.x - 20, torch.pos.y - 10);
                    } else {
                        TextureRegion torchFrame = animationManager.torchAnim.getKeyFrame(animationDelta, true);
                        batch.draw(torchFrame, torch.pos.x, torch.pos.y);
                    }
                }
            }
            for (Explosion explosion : explosions) {
                TextureRegion frame = animationManager.arrowExplodeAnim.getKeyFrame(explosion.getTimer(), false);
                batch.draw(frame, explosion.pos.x - 12, explosion.pos.y );
            }

            for (Actor actor : currentLevel.actors) {
                if (!actor.isHidden && actor.pos.y < threeDeeLinePos.y) {
                    drawActor(actor);
                }
            }

            batch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);
            TextureRegion tr = lightRenderer.getTextureRegion();
            batch.draw(tr, camera.position.x - (VIEWPORT_WIDTH / 2.0f), camera.position.y - (VIEWPORT_HEIGHT / 2.0f));
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            if (conversation != null) {
                Sprite selectArrowSprite = spriteManager.getSprite("selectArrowSprite");
                dialogContainer.render(batch, new Vector2(camera.position.x - (VIEWPORT_WIDTH / 2.0f) + 100, camera.position.y - (VIEWPORT_HEIGHT / 2.0f) + 100), conversation, soundPlayer);
                selectArrowSprite.setPosition(dialogContainer.lastPos.x + 8, dialogContainer.lastPos.y + 12);
                selectArrowSprite.setRegion(animationManager.selectArrowAnim.getKeyFrame(animationDelta, true));
                selectArrowSprite.draw(batch);
            } else {
                dialogContainer.reset();
            }
            if (posterImageName != null) {
                Sprite posterSprite = spriteManager.getSprite("posterSprite");
                if (posterImageName.equals("posters/poster-prize.png")) {
                    stonePrizeScene.update(this);
                    Vector2 pos = new Vector2(camera.position.x - 150, camera.position.y - 120);
                    stonePrizeScene.render(batch, pos, posterAlpha);
                } else {
                    if (posterImageName.equals("posters/vaporwave.png")) {
//                        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                        posterSprite.setTexture(assetManager.get(posterImageName));
                        posterSprite.setAlpha(posterAlpha);
                        posterSprite.setScale(1f);
                        posterSprite.setPosition(camera.position.x - (VIEWPORT_WIDTH / 2.0f), camera.position.y - (VIEWPORT_HEIGHT / 2.0f));
                        posterSprite.draw(batch);
                    } else {
                        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                        posterSprite.setTexture(assetManager.get(posterImageName));
                        posterSprite.setAlpha(posterAlpha);
                        posterSprite.setScale(1);
                        posterSprite.setPosition(camera.position.x - (VIEWPORT_WIDTH / 2.0f), camera.position.y - (VIEWPORT_HEIGHT / 2.0f));
                        posterSprite.draw(batch);
                    }
                }
            }
            batch.end();
        }
        if (isPlayingOpeningScene) {
            newGameScene.update(this);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            Vector2 pos = new Vector2(camera.position.x - 100, camera.position.y - 10);
            newGameScene.render(batch, pos);
            batch.end();
        }
        if (menu.shouldHandleMenu() && !isViewDirty && !isPlayingOpeningScene) {
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            menuRenderer.render(batch, animationDelta, this);
            if (menu.showSaveWarning && conversation != null) {
                conversation.update();
                dialogContainer.render(batch, new Vector2(camera.position.x - (VIEWPORT_WIDTH / 2.0f), camera.position.y - (VIEWPORT_HEIGHT / 2.0f)), conversation, soundPlayer);
                if (conversation.getCurrentDialog().isFinished()) {
                    Sprite selectArrowSprite = spriteManager.getSprite("selectArrowSprite");
                    selectArrowSprite.setPosition(dialogContainer.lastPos.x + 8, dialogContainer.lastPos.y + 12);
                    selectArrowSprite.draw(batch);
                }
            }
            batch.end();
        }
        Vector2 pos = new Vector2(204, 180);
        if (!menu.shouldHandleMenu() || isBrightnessOption()) {
            pos = new Vector2(camera.position.x - 76, camera.position.y - 60);
        }
        batch.begin();
        Sprite fadeSprite = spriteManager.getSprite("fadeSprite");
        fadeSprite.setColor(fadeColor);
        fadeSprite.setAlpha(screenFade);
        fadeSprite.setPosition(pos.x, pos.y);
        fadeSprite.draw(batch);
        batch.end();

        isLevelDirty = false;
        isViewDirty = false;
    }

    private void updateCameraZoom() {
        float targetZoom;
        if ((conversation == null && currentScenes.isEmpty()) || menu.showSaveWarning) {
            targetZoom = 0.7f;
        } else {
            targetZoom = 0.6f;
        }
        if (sceneTargetZoom > 0) {
            targetZoom = sceneTargetZoom;
        }
        if (menu.shouldHandleMenu()) {
            targetZoom = 0.9f;
        }
        if (posterImageName != null) {
            targetZoom = 1.0f;
        }
        if (Math.abs(camera.zoom - targetZoom) < ZOOM_THRESHOLH) {
            camera.zoom = targetZoom;
        }
        if (camera.zoom < targetZoom) {
            camera.zoom = camera.zoom + ZOOM_AMOUNT;
        }
        if (camera.zoom > targetZoom) {
            camera.zoom = camera.zoom - ZOOM_AMOUNT;
        }
    }

    private void drawActor(Actor actor) {
        TextureRegion currentFrame = null;
        Sprite antSprite = spriteManager.getSprite("antSprite");
        antSprite.setSize(32, 32);
        if (antAnim.equals("normal")) {
            if (actor.isWalking) {
                currentFrame = animationManager.antWalk.getKeyFrame(animationDelta, true);
            } else {
                currentFrame = animationManager.antIdle.getKeyFrame(animationDelta, true);
            }
        } else {
            if (antAnim.equals("drink")) {
                currentFrame = animationManager.antDrink.getKeyFrame(antAnimDelta, false);
            }
            if (antAnim.equals("fall")) {
                antSprite.setSize(48, 48);
                currentFrame = animationManager.antFall.getKeyFrame(antAnimDelta, false);
            }
            if (antAnim.equals("prepare")) {
                currentFrame = animationManager.antPrepare.getKeyFrame(antAnimDelta, true);
            }
            if (antAnim.equals("appear")) {
                currentFrame = animationManager.antAppear.getKeyFrame(antAnimDelta, false);
            }
            if (antAnim.equals("disappear")) {
                currentFrame = animationManager.antDisappear.getKeyFrame(antAnimDelta, false);
            }
        }
        antSprite.setPosition(actor.pos.x, actor.pos.y + 12);
        antSprite.setRegion(currentFrame);
        antSprite.flip(!actor.isFacingRight, false);
        antSprite.draw(batch);
    }

    private void drawEnemy(Enemy enemy, float height) {
        if (enemy.isGround()) {
            return;
        }
        TextureRegion frame = animationManager.enemyIdle.getKeyFrame(enemy.animTimer, true);
        if (enemy.getDir().x != 0) {
            frame = animationManager.enemyIdleHor.getKeyFrame(enemy.animTimer, true);
        }
        if (enemy.isShooting) {
            frame = animationManager.enemyShoot.getKeyFrame(enemy.animTimer, false);
            if (enemy.getDir().x != 0) {
                frame = animationManager.enemyShootHor.getKeyFrame(enemy.animTimer, false);
            }
        }
        Sprite enemySprite = spriteManager.getSprite("enemySprite");
        enemySprite.setRegion(frame);
        enemySprite.setSize(TILE_SIZE + 2, TILE_SIZE + 2);
        enemySprite.setOrigin(17, 17);
        enemySprite.setPosition(enemy.pos.x - 1, enemy.pos.y + 4);
        if (enemy.getDir().x > 0) {
            enemySprite.setFlip(true, false);
        }
        enemySprite.draw(batch);
    }

    @Override
    public void dispose () {
        batch.dispose();
        assetManager.dispose();
        lightRenderer.dispose();
        soundPlayer.dispose();
    }

    private void update() {
        statisticsManager.update();
        soundPlayer.update(playerPos);
        boolean blocksDirty = false;
        if (playerWasPushing) {
            playerWasPushing = false;
        }
        if (playerDeathTimer > 0) {
            playerDeathTimer = playerDeathTimer - Gdx.graphics.getDeltaTime();

        } else {
            if (playerIsDead) {
                restartLevel();
            }
        }
        if (playerShootingTimer > 0) {
            playerShootingTimer = playerShootingTimer - Gdx.graphics.getDeltaTime();
            if (playerShootingTimer < 0) {
                isPlayerShooting = false;
                playerShootingTimer = 0;
            }
            if (playerShootingTimer < 0.1f) {
                castCurrentSpell();
            }
        }
        if (!currentScenes.isEmpty()) {
            playerMovement = playerSceneMovement.cpy();
        }
        if (isMoving() && !playerIsDead) {
            float movementDelta = Gdx.graphics.getDeltaTime();
            walkAnimDelta = walkAnimDelta + movementDelta;
            playerPos.add(playerMovement);
            if (currentPlatform != null && !isMoving()) {
                playerPos.add(currentPlatform.getMovement());
            }
        } else {
            wasMoving = true;
            playerWasPushing = playerIsPushing;
            playerIsPushing = false;
            lastBlock = null;
            isWalkOne = !isWalkOne;
        }
        if (lastBlock != null) {
            if (!lastBlock.isMoving()) {
                playerIsPushing = false;
                playerWasPushing = true;
                lastBlock = null;
            }
        }
        for (Door door : currentLevel.doors) {
            door.update();
        }
        if (lazerSoundTimer > 0) {
            lazerSoundTimer = lazerSoundTimer - Gdx.graphics.getDeltaTime();
        }
        if (stepTimer > 0) {
            stepTimer = stepTimer - Gdx.graphics.getDeltaTime();
        }
        if (loadLevelTimer > 0) {
            loadLevelTimer = loadLevelTimer - Gdx.graphics.getDeltaTime();
        }
        if (levelTransitionTimer < 0) {
            if (leaveLevel) {
                startLevel(nextLevel, nextConnection);
            }
            if (loadLevelTimer > 0) {
                setScreenFade(loadLevelTimer/LEVEL_TRANSITION_TIMER, Color.BLACK);
            } else {
                setScreenFade(0, Color.BLACK);
            }
        } else {
            levelTransitionTimer = levelTransitionTimer - Gdx.graphics.getDeltaTime();
            walkAnimDelta = walkAnimDelta + Gdx.graphics.getDeltaTime();
            Vector2 dir = leaveLevel ? nextConnection.dir.cpy() : lastConnection.dir.cpy();
            if (dir.x > 0) {
                playerFacingLeft = false;
            }
            if (dir.x < 0) {
                playerFacingLeft = true;
            }
            playerMovement.x = 0;
            playerMovement.y = 0;
            Vector2 movement = dir.scl(Gdx.graphics.getDeltaTime() * PLAYER_SPEED * PLAYER_TRANSITION_SPEED);
            playerPos.add(movement);
            if (!isBrightnessOption()) {
                if (!leaveLevel) {
                    setScreenFade(levelTransitionTimer/LEVEL_TRANSITION_TIMER, Color.BLACK);
                } else {
                    setScreenFade((LEVEL_TRANSITION_TIMER - levelTransitionTimer)/LEVEL_TRANSITION_TIMER, Color.BLACK);
                }
            }
            return;
        }

        for (PressureTile pressureTile : currentLevel.pressureTiles) {
            pressureTile.update();
            boolean handled = false;

            if (playerPos.dst2(pressureTile.pos) < 256) {
                pressureTile.handleAction(soundPlayer);
                handled = true;
            }
            for (BlockLike block : currentLevel.getBlockLikes()) {
                if (block.getPos().dst2(pressureTile.pos) < 64) {
                    pressureTile.handleAction(soundPlayer);
                    handled = true;
                }
            }
            if (!handled) {
                pressureTile.handlePressureOff(soundPlayer);
            }
        }
        for (Explosion explosion : explosions) {
            explosion.update();
        }
        explosions.removeIf(Explosion::isDone);

        if (!playerIsDead) {
            Platform platform = currentLevel.getPlatform(playerPos);
            if (platform != null) {
                currentPlatform = platform;
            } else {
                if (lockedPlatform != null) {
                    currentPlatform = lockedPlatform;
                } else {
                    currentPlatform = null;
                }
            }
            currentImageHeight = null;
            if (currentPlatform == null && isDeathPlayer()) {
                BlockLike block = currentLevel.getGroundBlock(playerPos);
                if (!(block != null && block.isGround())) {
                    playerDeathTimer = PLAYER_DEATH_TIME;
                    soundPlayer.playSound("music/scream-hurt.ogg", playerPos);
                    playerIsDead = true;
                    animationDelta = 0;
                    isFallDeath = true;
                    statisticsManager.addGameEvent(statisticsManager.playerDeathEvent("fall", currentLevel));
                }
                if (block != null && block.isGround()) {
                    currentImageHeight = block;
                }

            }
            lockedPlatform = null;
        }

        if (currentPlatform != null && playerMovement.isZero()) {
            playerPos = playerPos.add(currentPlatform.getMovement().cpy());
        }

        for (Connection connection : currentLevel.connections) {
            Vector2 movePos = playerPos.cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE).add(playerDir.cpy().scl(QUARTER_TILE_SIZE));
            if (connection.contains(movePos)) {
                if (connection.active) {
                    if (connection.to != null && !connection.to.isEmpty()) {
                        goToConnection(connection.to);
                        return;
                    }
                }
            } else {
                connection.active = true;
            }
        }

        for (Enemy enemy : currentLevel.enemies) {
            enemy.update(playerPos.cpy().add(HALF_TILE_SIZE,HALF_TILE_SIZE), this);
        }

        DialogSource dialogSource = currentLevel.getDialogSource(playerPos);
        if (dialogSource != null) {
            startDialog(dialogSource.id, null);
            dialogSource.done = true;
            playerMovement.x = 0;
            playerMovement.y = 0;
        }
        if (conversation != null) {
            conversation.update();
        }
        for (Actor actor : currentLevel.actors) {
            actor.isWalking = false;
            if (actor.isBoss) {
                Platform platform = currentLevel.getPlatform(actor.pos);
                if (platform != null) {
                    actor.pos = platform.pos.cpy();
                }
                actor.update(this, platform);
            }
        }
        checkForSceneSources(playerPos.cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE));
        currentLevel.resetSceneSources(playerPos);
        List<Scene> newScenes = new ArrayList<>();
        Iterator<Scene> sceneIterator = currentScenes.iterator();
        while (sceneIterator.hasNext()) {
            Scene scene = sceneIterator.next();
            scene.update(this);
            if (scene.isDone()) {
                String outcome = scene.getOutcome();
                if (outcome != null) {
                    if (sceneContainer.scenes.containsKey(outcome)) {
                        newScenes.add(sceneContainer.scenes.get(outcome));
                    }
                }
                scene.start();
                sceneIterator.remove();
            }
        }
        currentScenes.addAll(newScenes);

        for (ArrowSource arrowSource : currentLevel.getArrowSources()) {
            arrowSource.update(this);
        }

        Iterator<Arrow> arrowIterator = arrows.iterator();
        while(arrowIterator.hasNext()) {
            Arrow arrow = arrowIterator.next();
            arrow.update();
            for (Arrow otherArrow : arrows) {
                if (otherArrow != arrow && otherArrow.getRect().overlaps(arrow.getRect())) {
                    if (!otherArrow.isRed && !arrow.isRed) {
                        otherArrow.isDead = true;
                        arrow.isDead = true;
                    }
                }
            }

            Vector2 actualArrowPos = arrow.pos.cpy().add(QUARTER_TILE_SIZE, QUARTER_TILE_SIZE);
            if (arrow.isDead || currentLevel.isWall(actualArrowPos) || currentLevel.isOutOfBounds(actualArrowPos) || currentLevel.getDoor(actualArrowPos, false) != null) {
                explosions.add(new Explosion(arrow.pos.cpy()));
                soundPlayer.playSound("music/blast-1.ogg", arrow.pos);
                arrowIterator.remove();
                continue;
            }
            BlockLike block = currentLevel.getBlockLike(arrow.pos.cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE), true);
            if (block != null) {
                blocksDirty = true;
                Vector2 nextTileAgain = arrow.pos.cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE).add(arrow.dir.cpy().scl(TILE_SIZE));
                explosions.add(new Explosion(arrow.pos.cpy()));
                if (currentLevel.isTileBlocked(nextTileAgain)) {
                    arrowIterator.remove();
                } else {
                    arrowIterator.remove();
                    block.move(arrow.dir);
                }
                soundPlayer.playSound("music/blast-1.ogg",block.getPos());
                soundPlayer.playSound("music/block-3.ogg", block.getPos());
            }
            if (!playerIsDead && getPlayerRect().overlaps(arrow.getRect())) {
                playerDeathTimer = PLAYER_DEATH_TIME;
                playerIsDead = true;
                soundPlayer.playSound("music/flame-0.ogg", arrow.pos);
                animationDelta = 0;
                isFallDeath = false;
                statisticsManager.addGameEvent(statisticsManager.playerDeathEvent("arrow", currentLevel));
                return;
            }
            for (Actor actor : currentLevel.actors) {
                if (actor.isBoss) {
                    if (arrow.getRect().overlaps(actor.getHitRect()) && actor.canBeHit()) {
                        actor.handleHit();
                        explosions.add(new Explosion(arrow.pos.cpy()));
                        soundPlayer.playSound("music/blast-1.ogg", arrow.pos);
                        arrowIterator.remove();
                    }
                }
            }
        }

        for (Platform platform : currentLevel.getPlatforms()) {
            platform.update(soundPlayer, this);
        }

        effects.forEach(MyEffect::update);
        effects.removeIf(e -> e.timer > e.life);

        if (castCooldown > 0) {
            castCooldown = castCooldown - Gdx.graphics.getDeltaTime();
        }


        for (Block block : currentLevel.blocks) {
            block.update();
        }
        for (BlockLike blockLike : currentLevel.getBlockLikes()) {
            if (!blockLike.isMoving() && !blockLike.isGround()) {
                Platform platform = currentLevel.getPlatform(blockLike.getPos());
                if (platform == null) {
                    blockLike.setPos(new Vector2(tileRound(blockLike.getPos().x), tileRound(blockLike.getPos().y)));
                    if (currentLevel.isDeath(blockLike.getPos().cpy().add(QUARTER_TILE_SIZE, QUARTER_TILE_SIZE))) {
                        boolean alreadyGround = false;
                        for (Block otherBlock : currentLevel.blocks) {
                            if (otherBlock.isGround()) {
                                if (blockLike.getPos().dst2(otherBlock.getPos()) < 64) {
                                    alreadyGround = true;
                                }
                            }
                        }
                        if (!alreadyGround) {
                            blocksDirty = true;
                            blockLike.setGround(true);
                            soundPlayer.playSound("music/switch-1.ogg", blockLike.getPos());
                        }
                    }
                } else {
                    blockLike.setPos(platform.pos.cpy());
                }
            }
        }
        if (blocksDirty) {
            currentLevel.blocks.sort((o1, o2) -> o1.isGround() == o2.isGround() ? 0 : (o1.isGround() ? -1 : 1));
        }
    }



    private float tileRound(float in) {
        return MathUtils.round(in / TILE_SIZE) * TILE_SIZE;
    }

    protected void playScene(String id) {
        if (sceneContainer.scenes.containsKey(id)) {
            Scene scene = sceneContainer.scenes.get(id);
            if (!currentScenes.contains(scene)) {
                currentScenes.add(scene);
            }
            scene.start();
        }
    }

    private void checkForSceneSources(Vector2 pos) {
        List<SceneSource> sceneSources = currentLevel.getSceneSources(pos);
        for (SceneSource sceneSource : sceneSources) {
            if (sceneContainer.scenes.containsKey(sceneSource.id) &&
                    !currentScenes.contains(sceneContainer.scenes.get(sceneSource.id))) {
                Scene scene = sceneContainer.scenes.get(sceneSource.id);
                currentScenes.add(scene);
            }
        }
    }

    private void restartLevel() {
        startLevel(currentLevel, lastConnection);
    }

    private void goToMatchingConnection(String number) {
        Level level = levelManager.getLevelFromNumber(number);
        if (level == null) {
            return;
        }
        for (Connection thisConnection : currentLevel.connections) {
            for (Connection connectionOther : level.connections) {
                if (thisConnection.to.equals(connectionOther.name)) {
                    goToConnection(thisConnection.to);
                    return;
                }
            }
        }
    }

    private void goToNextLevel() {
        String nextNumber = String.valueOf(Integer.valueOf(currentLevel.number) + 1);
        goToMatchingConnection(nextNumber);
    }

    private void goToPreviousLevel() {
        String nextNumber = String.valueOf(Integer.valueOf(currentLevel.number) - 1);
        goToMatchingConnection(nextNumber);
    }

    private Rectangle getPlayerRect() {
        float buffer = TILE_SIZE * 0.2f;
        float playerSize = TILE_SIZE - buffer - buffer;
        return new Rectangle(playerPos.x + buffer, playerPos.y + buffer, playerSize, playerSize);
    }

    private void getInput() {
        boolean isLeftPressed = Gdx.input.isKeyPressed(inputProcessor.keyMappings.get("left key"));
        boolean isRightPressed = Gdx.input.isKeyPressed(inputProcessor.keyMappings.get("right key"));
        boolean isUpPressed = Gdx.input.isKeyPressed(inputProcessor.keyMappings.get("up key"));
        boolean isDownPressed = Gdx.input.isKeyPressed(inputProcessor.keyMappings.get("down key"));

        Vector2 inputAmount = new Vector2(1, 1);

        if (isLeftPressed) {
            inputVector.x = inputVector.x - 1;
        }
        if (isRightPressed) {
            inputVector.x = inputVector.x + 1;
        }
        if (isUpPressed) {
            inputVector.y = inputVector.y + 1;
        }
        if (isDownPressed) {
            inputVector.y = inputVector.y - 1;
        }

        if (menu.shouldHandleMenu() && menu.canHandleInput()) {
            menu.handleInput(this, soundPlayer, inputVector,inputProcessor);
        }

        if (conversation != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                inputVector.x = 1;
            }
            if ((inputVector.x != 0 || inputVector.y != 0) && !dialogLock) {
                soundPlayer.playSound("music/talk-beep.ogg", playerPos);
                if (!conversation.isFinished()) {
                    conversation.handleInput(inputVector);
                }
                if (conversation.isFinished()) {
                    String chosenOption = conversation.getCurrentDialog().getChosenOption();
                    if (menu.showSaveWarning) {
                        if (chosenOption.equals("new-game")) {
                            startOpeningScene();
                        } else {
                            gotoState("menu");
                        }
                    }
                    if (activeDialogVerb != null) {
                        activeDialogVerb.finish(chosenOption);
                        activeDialogVerb = null;
                        moveLock = true;
                        skipLock = true;
                    }
                    conversation = null;
                }
            }
        } else {
            if (!playerIsDead && levelTransitionTimer < 0 && playerSceneMovement.isZero()) {
                boolean sceneBlock = !currentScenes.isEmpty();
                if (!moveLock && !sceneBlock && !inputVector.isZero()) {
                    boolean blocked = false;
                    moveVector = inputVector.cpy();
                    Vector2 nextTilePos = playerPos.cpy()
                            .add(HALF_TILE_SIZE, HALF_TILE_SIZE)
                            .add(moveVector.cpy().scl(QUARTER_TILE_SIZE));
                    if (currentLevel.isTileBlocked(nextTilePos)) {
                        BlockLike block = currentLevel.getBlockLike(nextTilePos, true);
                        if (block == null) {
                            checkForSceneSources(nextTilePos);
                            blocked = true;
                        } else {
                            Vector2 nextTileAgain = nextTileAgain(moveVector);
                            if (currentLevel.isTileBlocked(nextTileAgain)) {
                                blocked = true;
                            } else {
                                if (moveVector.x != 0 && moveVector.y != 0) {
                                    Vector2 xTile = nextTileAgain(new Vector2(moveVector.x, 0));
                                    Vector2 yTile = nextTileAgain(new Vector2(0, moveVector.y));

                                    if (currentLevel.isTileBlocked(xTile)) {
                                        if (!currentLevel.isTileBlocked(yTile)) {
                                            // move y
                                            block.move(new Vector2(0, moveVector.y));
                                        }
                                    } else {
                                        // move x
                                        block.move(new Vector2(moveVector.x, 0));
                                    }
                                } else {
                                    block.move(moveVector);
                                }

                                lastBlock = block;
                                playerIsPushing = true;
                                soundPlayer.playSound("music/block-3.ogg", block.getPos());
                            }
                        }
                    }
                    Platform platform = currentLevel.getPlatform(playerPos.cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE));
                    if (platform != null) {
                        Vector2 nextNextTilePos = playerPos.cpy()
                                .add(moveVector.cpy().scl(QUARTER_TILE_SIZE))
                                .add(HALF_TILE_SIZE, HALF_TILE_SIZE);
                        if (currentLevel.isTileBlocked(nextNextTilePos)) {
                            BlockLike block = currentLevel.getBlockLike(nextNextTilePos, true);
                            if (block == null) {
                                blocked = true;
                            } else {
                                Vector2 nextTileAgain = playerPos.cpy()
                                        .add(moveVector.cpy().scl(TILE_SIZE + QUARTER_TILE_SIZE))
                                        .add(HALF_TILE_SIZE, HALF_TILE_SIZE);
                                if (currentLevel.isTileBlocked(nextTileAgain)) {
                                    blocked = true;
                                } else {
                                    block.move(moveVector);
                                    lastBlock = block;
                                    playerIsPushing = true;
                                    soundPlayer.playSound("music/block-3.ogg", block.getPos());
                                }
                                nextTileAgain = playerPos.cpy()
                                        .add(moveVector.cpy().scl(TILE_SIZE + TILE_SIZE + QUARTER_TILE_SIZE))
                                        .add(HALF_TILE_SIZE, HALF_TILE_SIZE);
                                if (currentLevel.isTileBlocked(nextTileAgain)) {
                                    blocked = true;
                                } else {
                                    block.move(moveVector);
                                    lastBlock = block;
                                    playerIsPushing = true;
                                    soundPlayer.playSound("music/block-3.ogg", block.getPos());
                                }
                            }
                        }
                    }
                    Platform nextPlatform = currentLevel.getPlatform(nextTilePos);
                    if (nextPlatform != null) {
                        lockedPlatform = nextPlatform;
                    }
                    if (!blocked && currentScenes.isEmpty()) {
                        if (!wasMoving) {
                            walkAnimDelta = 0;
                        }
                        if (inputVector.x < 0 && !playerFacingLeft) {
                            playerFacingLeft = true;
                        }
                        if (inputVector.x > 0 && playerFacingLeft) {
                            playerFacingLeft = false;
                        }
                        playerDir = inputVector.cpy();
                        playerMovement = inputVector.cpy().scl(2f).scl(inputAmount);
                    } else {
                        playerMovement.x = 0;
                        playerMovement.y = 0;
                    }
                }
                if (!inputVector.isZero() && !skipLock) {
                    skipLock = true;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    if (!sceneBlock && !castLock && !isPlayerShooting) {
                        isPlayerShooting = true;
                        playerShootingTimer = PLAYER_SHOOTING_TIME;
                        animationDelta = 0;
                    }
                    castLock = true;
                } else {
                    castLock = false;
                }
            }
        }
        if ((inputVector.x != 0 || inputVector.y != 0)) {
            dialogLock = true;
            if (isMoving()) {
                if (stepTimer < 0) {
                    soundPlayer.playSound("music/step-2.ogg", playerPos);
                    stepTimer = 0.165f * 4f;
                }
            }
        } else {
            dialogLock = false;
            moveLock = false;
            skipLock = false;
            wasMoving = false;
            menu.titleLock = false;
            playerMovement = new Vector2();
        }
        inputVector = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_9)) {
            gamma = gamma + 0.01f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_8)) {
            gamma = gamma - 0.01f;
        }
        gamma = MathUtils.clamp(gamma, 0, 1.0f);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (isPlayingOpeningScene) {
                newGameScene.finish();
            } else {
                menu.isTitleMenu = true;
                isViewDirty = true;
                menu.titleLock = true;
                soundPlayer.pauseSounds();
                loadLevelTimer = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            restartLevel();
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.COMMA) && !Gdx.input.isKeyPressed(Input.Keys.PERIOD)) {
            levelChangeLock = false;
        }
        if (!levelChangeLock && Gdx.input.isKeyPressed(Input.Keys.PERIOD)) {
            goToNextLevel();
            levelChangeLock = true;
        }
        if (!levelChangeLock && Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
            goToPreviousLevel();
            levelChangeLock = true;
        }
    }

    private Vector2 nextTileAgain(Vector2 mov) {
        return playerPos.cpy()
                .add(mov.cpy().scl(TILE_SIZE + QUARTER_TILE_SIZE))
                .add(HALF_TILE_SIZE, HALF_TILE_SIZE);
    }

    private void castCurrentSpell() {
        if (castCooldown > 0) {
            return;
        }
        float speedBoost = 1.0f;
        if (bossIsFighting()) {
            speedBoost = 2.0f;
        }
        if (currentSpell != null && currentSpell.equals("arrow")) {
            Vector2 nextTilePos = playerDir.cpy().scl(24f, 32f).add(playerPos).add(0, QUARTER_TILE_SIZE);
            addArrow(nextTilePos, playerDir, PLAYER_ARROW_SPEED * speedBoost, false);
            castCooldown = CAST_ARROW_COOLDOWN;
        }
    }

    public void startDialog(String id, DialogVerb dialogVerb) {
        conversation = new Conversation(dialogContainer.dialogs.get(id));
        activeDialogVerb = dialogVerb;
        conversation.reset();
        dialogLock = true;
    }

    public void setActorPos(String actor, Vector2 pos) {
        for (Actor levelActor : currentLevel.actors) {
            if (levelActor.id.equals(actor)) {
                levelActor.pos = pos.cpy();
            }
        }
    }

    public void moveActor(String actor, Vector2 value) {
        for (Actor levelActor : currentLevel.actors) {
            if (levelActor.id.equals(actor)) {
                levelActor.pos.add(value);
                levelActor.isWalking = true;
                if (value.x > 0) {
                    levelActor.isFacingRight = true;
                }
                if (value.x < 0) {
                    levelActor.isFacingRight = false;
                }
            }
        }
        if (actor.equals("pro")) {
            playerDir = value.cpy().nor();
            moveVector = value.cpy().nor();
//            playerMovement = value.cpy().nor();
            playerSceneMovement = value.cpy().nor();
            if (value.x < 0 && !playerFacingLeft) {
                playerFacingLeft = true;
            }
            if (value.x > 0 && playerFacingLeft) {
                playerFacingLeft = false;
            }
        }
    }

    public void moveCamera(Vector2 pos) {
        cameraTargetPos = pos.cpy();
        snaplock = true;
    }

    public void resetCamera() {
        snaplock = true;
        cameraTargetPos = null;
    }

    public void hideActor(String id, boolean isHide) {
        for (Actor levelActor : currentLevel.actors) {
            if (levelActor.id.equals(id)) {
                levelActor.isHidden = isHide;
            }
        }
        if (id.equals("pro")) {
            isHidePlayer = isHide;
        }
    }

    public Trunk getTrunk() {
        return currentLevel.trunk;
    }

    public void addArrow(Vector2 pos, Vector2 dir, float speed, boolean isRed) {
        soundPlayer.playSound("music/arrow-source.ogg", pos);
        arrows.add(new Arrow(true, pos, dir, speed, true, isRed));
    }

    public void addLazer(Vector2 pos, Vector2 dir) {
        if (lazerSoundTimer <= 0) {
            soundPlayer.playSound("music/lazer-4.ogg", pos);
            lazerSoundTimer = 0.5f;
        }
        arrows.add(new Arrow(false, pos.cpy().add(dir.x * HALF_TILE_SIZE, dir.y * HALF_TILE_SIZE), dir, TILE_SIZE * 16.0f, false, true));
    }

    public void showPoster(float alpha, String poster) {
        posterAlpha = MathUtils.clamp(alpha, 0f, 1f);
        posterImageName = poster;
        if (poster != null) {
            if (poster.equals("posters/poster-prize.png")) {
                currentSpell = "arrow";
            }
        } else {
            stonePrizeScene.reset();
            newGameScene.reset();
        }
    }

    public void setScreenFade(float amount, Color color) {
        screenFade = MathUtils.clamp(amount, 0, 1f);
        fadeColor = color;
    }

    public void fadeScreen(boolean inDirection, float time, Color color) {
        screenFader.fadeScreen(inDirection, time, color);
    }

    public void startOpeningScene() {
        isPlayingOpeningScene = true;
        menu.isTitleMenu = false;
        isHidePlayer = true;
        newGameScene.reset();
        soundPlayer.playSound("music/new-game-1.ogg", playerPos);
//        soundPlayer.levelMusic("the-visitor");
//        soundPlayer.playMusic("the-visitor");
    }

    @Override
    public void gotoState(String state) {
        if (state.equals("new-game")) {
            // openingScene

            menu.isTitleMenu = false;
            isHidePlayer = false;
            startLevel(levelManager.getLevel(START_LEVEL_NUM), levelManager.getLevel(START_LEVEL_NUM).getConnection("61"));

            currentSpell = "";
            saveEverything();

        }
        if (state.equals("menu")) {
            menu.isTitleMenu = true;
            isViewDirty = true;
            menu.titleLock = true;
        }
        if (state.equals("removeSpell")) {
            currentSpell = "";
        }

        menu.showSaveWarning = false;
    }

    @Override
    public void goToConnection(String target) {
        Level level = levelManager.getLevelFromConnection(target);
        Connection connection = level.getConnection(target);
        nextLevel = level;
        nextConnection = connection;
        levelTransitionTimer = LEVEL_TRANSITION_TIMER;
        leaveLevel = true;
        System.out.println("level " + level.name);
    }

    boolean isBrightnessOption() {
        int tempIndex = menu.optionOptions.size() - 1 - menu.titleSelectionIndex;
        return menu.isOptionsMenu && menu.optionOptions.get(tempIndex).equals("brightness");
    }

    public boolean isLazerBlocking(Vector2 checkPos) {
        return currentLevel.isLazerBlocked(checkPos);
    }

    public void playSound(int id, String name, Vector2 pos) {
        soundPlayer.playSound(id, name, pos, false);
    }

    public void playMusic(String name) {
        soundPlayer.levelMusic(name);
    }

    public void addEffect(String name, Vector2 pos, float life) {
        effects.add(new MyEffect(name, pos, life));
    }

    public void removeArrows() {
        arrows.clear();
    }

    public boolean isMoving() {
        return !playerMovement.isZero() || !playerSceneMovement.isZero();
    }

    public boolean isDeathPlayer() {
        boolean up = currentLevel.isDeathOrWall(playerPos.cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE + QUARTER_TILE_SIZE));
        boolean down = currentLevel.isDeathOrWall(playerPos.cpy().add(HALF_TILE_SIZE, HALF_TILE_SIZE - QUARTER_TILE_SIZE));
        boolean left = currentLevel.isDeathOrWall(playerPos.cpy().add(HALF_TILE_SIZE - QUARTER_TILE_SIZE, HALF_TILE_SIZE));
        boolean right = currentLevel.isDeathOrWall(playerPos.cpy().add(HALF_TILE_SIZE + QUARTER_TILE_SIZE, HALF_TILE_SIZE));
        return up && down && left && right;
    }

    public void hideGuff(String image, boolean hide) {
        for (Guff guff : currentLevel.guffs) {
            if (guff.imageName.equals(image)) {
                guff.setVisible(hide);
            }
        }
    }

    public void setAntAnim(String anim, float antAnimDelta) {
        antAnim = anim;
        this.antAnimDelta = antAnimDelta;
    }

    public void setAntPhase(Actor.Phase phase) {
        currentLevel.actors.get(0).setPhase(phase);
    }

    public void lockMovement(boolean moveLock) {
        this.moveLock = moveLock;
    }

    public float getGamma() {
        return gamma;
    }

    public void setGamma(float gamma) {
        this.gamma = gamma;
    }

    public Vector2 getPlayerPos() {
        return playerPos;
    }

    public void resumeGame() {
        dialogLock = true;
        loadLevelTimer = LEVEL_TRANSITION_TIMER;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public List<Arrow> getArrows() {
        return arrows;
    }

    public boolean isStaticLevel() {
        return staticLevel;
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }

    public void zoomCamera(float target) {
        sceneTargetZoom = target;
    }


    //	@Override
//	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        testpos.x = touches.get(0).touchX;
//        testpos.y = -touches.get(0).touchY + 1280;
//        testbatch.setProjectionMatrix(testcamera.combined);
//        testbatch.begin();
//        testbatch.draw(testimg, testpos.x, testpos.y);
//        testbatch.end();
//	}
//
}
