package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.entity.Platform;

import java.util.Arrays;
import java.util.List;

import static com.lovely.games.Constants.PLAYER_ARROW_SPEED;
import static com.lovely.games.Constants.TILE_SIZE;

public class Actor {

    public Vector2 pos;
    String id;
    public boolean isHidden;
    Vector2 originalPos;
    boolean originalIsHide;
    boolean isWalking;
    boolean isFacingRight;
    boolean isBoss;
    float shootTimer;
    int bossLives;
    private boolean wasHit = false;
    boolean isDone = false;
    private Phase phase;
    private float phaseTimer;
    public boolean showLight;
    private boolean hasShot;
    private float numShots;
    private List<Vector2> firePositions = Arrays.asList(new Vector2(340, 600), new Vector2(440, 300), new Vector2(600, 600), new Vector2(640, 340)) ;
    private int firePositionIndex;

    public enum Phase {
        TALKING,
        RANDOM_SPOT,
        PRE_APPEAR,
        APPEAR,
        SHOOT_FIREBALL,
        DISAPPEAR,
        WAITING,
        FIERY_DEATH
    }

    public Actor(Vector2 pos, String id, boolean isHide, boolean isRight, boolean isBoss) {
        this.pos = pos;
        this.id = id;
        this.originalIsHide = isHide;
        this.isHidden = isHide;
        this.originalPos = pos.cpy();
        this.isWalking = false;
        this.isFacingRight = isRight;
        this.isBoss = isBoss;
        this.shootTimer = 0;
        this.bossLives = 3;
        this.phase = Phase.DISAPPEAR;
        this.phaseTimer = 0;
        showLight = true;
        firePositionIndex = 0;
        this.pos = new Vector2(MathUtils.random(450, 750), MathUtils.random(250, 550));
    }

    public void start() {
        this.pos = originalPos.cpy();
        this.isHidden = originalIsHide;
        this.shootTimer = 0;
        this.bossLives = 3;
        wasHit = false;
        isDone = false;
        isWalking = false;
        this.phase = Phase.TALKING;
        this.phaseTimer = 0;
        showLight = true;
        hasShot = false;
        numShots = 0f;
    }

    public void update(CaenMain stage, Platform platform) {
        if (isDone) {
            return;
        }
        switch (phase) {
            case TALKING:
                isHidden = false;
                showLight = true;
                stage.setAntAnim("normal", phaseTimer);
                break;
            case RANDOM_SPOT:
                isHidden = true;
                showLight = false;
                stage.setAntAnim("prepare", phaseTimer);
                this.pos = getRandomSafePos();
                phase = Phase.PRE_APPEAR;
                phaseTimer = 0;
                System.out.println("pre-appear");
                break;
            case PRE_APPEAR:
                isHidden = false;
                showLight = false;
                stage.setAntAnim("prepare", phaseTimer);
                if (phaseTimer > 0.4f) {
                    phase = Phase.APPEAR;
                    System.out.println("appear");
                    phaseTimer = 0;
                }
                break;
            case APPEAR:
                showLight = true;
                stage.setAntAnim("appear", phaseTimer);
                if (phaseTimer > 0.6f) {
                    hasShot = false;
                    numShots = 0f;
                    phase = Phase.SHOOT_FIREBALL;
                    System.out.println("shoot fireball");
                    phaseTimer = 0;
                }
                break;
            case SHOOT_FIREBALL:
                showLight = true;
                stage.setAntAnim("normal", phaseTimer);
                if (!hasShot) {
                    hasShot = true;
                    Vector2 direction = stage.getPlayerPos().cpy().sub(pos).nor();
                    Vector2 arrowPos = pos.cpy().add(direction.cpy().scl(24));
                    stage.addArrow(arrowPos, direction, PLAYER_ARROW_SPEED * 2f, true);
                    numShots = numShots + 1f;
                }
                if (phaseTimer > 0.3f * numShots && numShots < 3) {
                    hasShot = false;
                }
                if (phaseTimer > 1.2f) {
                    phase = Phase.DISAPPEAR;
                    System.out.println("disappear");
                    phaseTimer = 0;
                }
                break;
            case DISAPPEAR:
                showLight = true;
                stage.setAntAnim("disappear", phaseTimer);
                if (phaseTimer > 0.6f) {
                    phase = Phase.WAITING;
                    System.out.println("waiting");
                    phaseTimer = 0;
                }
                break;
            case WAITING:
                isHidden = true;
                if (phaseTimer > 1.2f) {
                    System.out.println("random spot");
                    phase = Phase.RANDOM_SPOT;
                    phaseTimer = 0;
                }
                showLight = false;
                break;
            case FIERY_DEATH:
                break;
        }

        phaseTimer = phaseTimer + Gdx.graphics.getDeltaTime();

        if (wasHit) {
            wasHit = false;
            bossLives = bossLives - 1;
            phase = Phase.DISAPPEAR;
            System.out.println("disappear");
            phaseTimer = 0;
            String lightName = "c";
            if (bossLives == 1) {
                lightName = "d";
            }
            if (bossLives == 0) {
                lightName = "e";
            }
            if (bossLives == -1) {
                lightName = "f";
            }
            stage.getTrunk().broadcast(lightName);
        }
        if (bossLives < 0) {
            stage.playScene("29");
            isDone = true;
            stage.removeArrows();
        }
    }

    private Vector2 getRandomSafePos() {
        firePositionIndex++;
        if (firePositionIndex == firePositions.size()) {
            firePositionIndex = 0;
        }
        return firePositions.get(firePositionIndex);
    }

    protected Rectangle getHitRect() {
        float buffer = TILE_SIZE * 0.2f;
        float playerSize = TILE_SIZE - buffer - buffer;
        return new Rectangle(pos.x + buffer, pos.y + buffer, playerSize, playerSize);
    }

    public void handleHit() {
        if (isBoss){
            wasHit = true;
        }
    }

    public boolean canBeHit() {
        return (phase.equals(Phase.SHOOT_FIREBALL) || phase.equals(Phase.APPEAR)) || phase.equals(Phase.DISAPPEAR);
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
        phaseTimer = 0;
        System.out.println(phase);
    }

}
