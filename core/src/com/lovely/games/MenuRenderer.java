package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class MenuRenderer {

    private Color fontColorSelectedMain = new Color(69 / 256.0f, 128 / 256.0f, 213 / 256.0f, 1);
    private Color fontColorMain = new Color(10 / 256.0f, 64 / 256.0f, 97 / 256.0f, 1);
    private Color fontGreyedOut = new Color(55 / 256.0f, 55 / 256.0f, 55 / 256.0f, 1);
    private Menu menu;
    private BitmapFont font;
    private SpriteManager spriteManager;
    private AnimationManager animationManager;

    public MenuRenderer(Menu menu, SpriteManager spriteManager, AnimationManager animationManager) {
        this.menu = menu;
        this.font = loadFonts("fonts/kells.fnt");
        this.spriteManager = spriteManager;
        this.animationManager = animationManager;
    }

    private BitmapFont loadFonts(String fontString) {
        BitmapFont font = new BitmapFont(Gdx.files.internal(fontString),false);
        font.setUseIntegerPositions(false);
        font.setColor(fontColorMain);
        font.getData().setScale(1.4f, 1.4f);
        return font;
    }

    public void render(SpriteBatch batch, float animationDelta, CaenMain caenMain) {
        if (menu.isTitleMenu) {
            Sprite titleSprite = spriteManager.getSprite("titleSprite");
            titleSprite.setPosition(180, 300);
            titleSprite.draw(batch);
        }
        Vector2 selectedPos = new Vector2(280, 200);
        List<String> menuOptions = menu.titleOptions;
        if (menu.isOptionsMenu) {
            menuOptions = menu.optionOptions;
            selectedPos.x = 140;
            selectedPos.y = 430;
        }
        if (menu.isCreditsMenu) {
            menuOptions = menu.creditOptions;
            selectedPos.x = 280;
        }
        Sprite selectArrowSprite = spriteManager.getSprite("selectArrowSprite");
        for (int index = menuOptions.size() - 1; index > -1; index--) {
            String option = menuOptions.get(index);
            float tmpfloat = 0;
            if (menu.titleSelectionIndex == menuOptions.size() - 1 - index) {
                font.setColor(fontColorSelectedMain);
                selectArrowSprite.setPosition(selectedPos.x + 80, selectedPos.y - 15);
                selectArrowSprite.setRegion(animationManager.selectArrowAnim.getKeyFrame(animationDelta, true));
                selectArrowSprite.draw(batch);
            } else {
                font.setColor(fontColorMain);
            }
            if (option.equals("continue") && !caenMain.hasContinue) {
                font.setColor(fontGreyedOut);
            }
            if (option.equals("reset everything!")) {
                tmpfloat = 200;
                if (menu.titleSelectionIndex == menuOptions.size() - 1 - index) {
                    font.setColor(new Color(1.0f, 0.4f, 0.8f, 1.0f));
                } else {
                    font.setColor(new Color(0.5f, 0.2f, 0.4f, 1.0f));
                }
            }
            if (caenMain.keyMappings.containsKey(option)) {
                if (option.equals(menu.pressKeyPlease)) {
                    font.draw(batch, "press key", selectedPos.x + 240, selectedPos.y, 0f, 1, false);
                } else {
                    font.draw(batch, Input.Keys.toString(caenMain.keyMappings.get(option)), selectedPos.x + 240, selectedPos.y, 0f, 1, false);
                }
            }

            font.draw(batch, option, selectedPos.x + tmpfloat, selectedPos.y, 0f, 1, false);
            selectedPos.add(0, -40);
        }
        if (menu.isOptionsMenu) {
            drawVolumeLine(batch, new Vector2(260, 178), caenMain.gamma);
            drawVolumeLine(batch, new Vector2(260, 138), caenMain.soundPlayer.getSoundVolume());
            drawVolumeLine(batch, new Vector2(260, 98), caenMain.soundPlayer.getMusicVolume());
        }
    }

    private void drawVolumeLine(SpriteBatch batch, Vector2 pos, float amount) {
        Sprite volumeLevelOnSprite = spriteManager.getSprite("volumeLevelOnSprite");
        Sprite volumeLevelOffSprite = spriteManager.getSprite("volumeLevelOffSprite");
        Sprite volumePointerSprite = spriteManager.getSprite("volumePointerSprite");
        for (int i = 0; i < 10; i++) {
            if (amount * 10 > i) {
                volumeLevelOnSprite.setPosition(pos.x + i * 32, pos.y );
                volumeLevelOnSprite.draw(batch);
            } else {
                volumeLevelOffSprite.setPosition(pos.x + i * 32, pos.y);
                volumeLevelOffSprite.draw(batch);
            }
            volumePointerSprite.setPosition(pos.x + ((amount * 10) * 32), pos.y );
            volumePointerSprite.draw(batch);
        }
    }
}
