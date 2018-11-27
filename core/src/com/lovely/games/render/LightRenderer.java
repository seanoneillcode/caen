package com.lovely.games.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.*;
import com.lovely.games.entity.Torch;

import static com.lovely.games.Constants.TILE_SIZE;
import static com.lovely.games.Constants.VIEWPORT_HEIGHT;
import static com.lovely.games.Constants.VIEWPORT_WIDTH;

public class LightRenderer {

    private SpriteBatch bufferBatch;
    private AnimationManager animationManager;
    private SpriteManager spriteManager;
    private FrameBuffer buffer;

    public LightRenderer(AnimationManager animationManager, SpriteManager spriteManager) {
        this.bufferBatch = new SpriteBatch();
        this.animationManager = animationManager;
        this.spriteManager = spriteManager;
        this.buffer = new FrameBuffer(Pixmap.Format.RGBA8888, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, false);
    }

    public void dispose() {
        bufferBatch.dispose();
        buffer.dispose();
    }

    public TextureRegion getTextureRegion() {
        TextureRegion tr = new TextureRegion(buffer.getColorBufferTexture());
        tr.flip(false,true);
        return tr;
    }

    public void render(Level currentLevel, float animationDelta, CaenMain caenMain) {
        buffer.begin();
        if (currentLevel.name.equals("levels/maze-1.tmx") ) {
            Gdx.gl.glClearColor(0, 0, 0, 1.0f);
        } else {
            Gdx.gl.glClearColor(caenMain.getGamma(), caenMain.getGamma(), caenMain.getGamma(), 1.0f);
        }
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        OrthographicCamera camera = caenMain.getCamera();
        float oldZoom = camera.zoom;
        camera.zoom = 0.95f;
        camera.update(false);
        bufferBatch.setProjectionMatrix(camera.combined);
        bufferBatch.begin();

        bufferBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);

        Vector2 offset = new Vector2(caenMain.getPlayerPos().x, caenMain.getPlayerPos().y);
        TextureRegion playerRegion = animationManager.playerLightAnim.getKeyFrame(animationDelta, true);
        Sprite playerLight = spriteManager.getSprite("playerLight");
        Sprite lightHole = spriteManager.getSprite("lightHole");
        Sprite levelLight = spriteManager.getSprite("levelLight");
        if (!caenMain.isStaticLevel()) {
            playerLight.setRegion(playerRegion);
            playerLight.setBounds(0,0,64,64);
            playerLight.setColor(1.0f, 0.8f, 0.5f, 1.0f);
            playerLight.setPosition( offset.x + 27, offset.y - 27);
            playerLight.draw(bufferBatch);
        }

        TextureRegion tr = animationManager.lightAnim.getKeyFrame(animationDelta, true);
        TextureRegion slow = animationManager.lightAnim.getKeyFrame(animationDelta * 2.0f, true);
        for (com.lovely.games.entity.Arrow arrow : caenMain.getArrows()) {
            lightHole.setColor(arrow.color);
            lightHole.setRegion(tr);
            lightHole.setPosition((arrow.pos.x), (arrow.pos.y));
            lightHole.draw(bufferBatch);
        }
        for (com.lovely.games.entity.ArrowSource arrowSource : currentLevel.arrowSources) {
            float animTime = arrowSource.getAnimTimer();
            if (animTime > 0 && !arrowSource.isHidden) {
                lightHole.setColor(new Color(0.3f, 0.8f, 0.6f, (animTime / 0.8f)));
                lightHole.setRegion(tr);
                lightHole.setPosition((arrowSource.pos.x), (arrowSource.pos.y));
                lightHole.draw(bufferBatch);
            }
        }
        for (com.lovely.games.entity.Explosion explosion : caenMain.getExplosions()) {
            lightHole.setColor(explosion.color);
            lightHole.setAlpha(1 - explosion.getAlpha() * 0.8f);
            lightHole.setRegion(tr);
            lightHole.setPosition((explosion.pos.x - 12), (explosion.pos.y));
            lightHole.setScale((explosion.getAlpha() * 4) * 6.0f);
            lightHole.draw(bufferBatch);
        }
        lightHole.setScale(6.0f);


        for (com.lovely.games.entity.PressureTile tile : currentLevel.pressureTiles) {
            lightHole.setColor(tile.color);
            lightHole.setRegion(tr);
            lightHole.setPosition((tile.pos.x), (tile.pos.y));
            lightHole.draw(bufferBatch);
        }
        for (com.lovely.games.entity.Platform platform : currentLevel.platforms) {
            lightHole.setColor(platform.color);
            lightHole.setRegion(tr);
            lightHole.setPosition((platform.pos.x), (platform.pos.y));
            lightHole.draw(bufferBatch);
        }
        for (com.lovely.games.entity.Block block : currentLevel.blocks) {
            lightHole.setColor(block.color);
            lightHole.setRegion(tr);
            lightHole.setPosition((block.pos.x), (block.pos.y));
            lightHole.draw(bufferBatch);
        }
        for (com.lovely.games.entity.Door door : currentLevel.doors) {
            lightHole.setColor(door.color);
            lightHole.setRegion(tr);
            lightHole.setPosition((door.pos.x), (door.pos.y + 16));
            lightHole.draw(bufferBatch);
        }
        for (LevelLight light : currentLevel.lights) {
            if (light.isActive) {
                levelLight.setColor(light.color);
                levelLight.setBounds(light.pos.x, light.pos.y, light.size.x, light.size.y);
                levelLight.draw(bufferBatch);
            }
        }
        for (com.lovely.games.entity.Enemy enemy : currentLevel.enemies) {
            lightHole.setColor(enemy.color);
            lightHole.setRegion(tr);
            lightHole.setPosition((enemy.pos.x), (enemy.pos.y));
            lightHole.draw(bufferBatch);
        }
        for (Torch torch : currentLevel.torches) {
            if (torch.isOn) {
                if (torch.isFire) {
                    lightHole.setColor(torch.color);
                    lightHole.setRegion(slow);
                    lightHole.setPosition((torch.pos.x), (torch.pos.y));
                    lightHole.draw(bufferBatch);
                } else {
                    lightHole.setColor(torch.color);
                    lightHole.setRegion(tr);
                    lightHole.setPosition((torch.pos.x), (torch.pos.y));
                    lightHole.draw(bufferBatch);
                }
            }
        }
        if (!caenMain.isStaticLevel()) {
            for (Actor actor : currentLevel.actors) {
                if (!actor.isHidden) {
                    playerLight.setRegion(playerRegion);
                    playerLight.setColor(1.0f, 0.8f, 0.5f, 1.0f);
                    playerLight.setPosition(actor.pos.x - 60, actor.pos.y);
                    playerLight.draw(bufferBatch);
                }
            }
        }
        camera.zoom = oldZoom;
        camera.update(false);

        bufferBatch.end();
        buffer.end();
    }


}
