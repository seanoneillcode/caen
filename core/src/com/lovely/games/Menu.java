package com.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.scene.DialogVerb;

import java.util.Arrays;
import java.util.List;

import static com.lovely.games.Constants.BLIP_SELECT_ITEM_SOUND_ID;
import static com.lovely.games.Constants.LEVEL_TRANSITION_TIMER;

public class Menu {

    public List<String> titleOptions = Arrays.asList("credits", "options", "new game", "load game");
    public List<String> optionOptions = Arrays.asList("back", "music", "sound", "brightness", "cast key", "down key", "right key", "left key", "up key", "reset everything!");
    public List<String> creditOptions = Arrays.asList("Music - Daniel Lacey",  "Quality - Ben Kirimlidis", "Quality - Michalis Kirimlidis", "Code and Art - Sean O'Neill");

    public boolean isTitleMenu = false;
    public boolean isOptionsMenu = false;
    public boolean isCreditsMenu = false;
    public int titleSelectionIndex = 0;
    public boolean titleLock = false;
    public String pressKeyPlease;
    public boolean showSaveWarning = false;
    public boolean hasContinue = false;

    public boolean canHandleInput() {
        return !titleLock && !showSaveWarning;
    }

    public boolean shouldHandleMenu() {
        return isTitleMenu || isOptionsMenu || isCreditsMenu;
    }

    public void handleInput(CaenMain caenMain, SoundPlayer soundPlayer, Vector2 inputVector, MyInputProcessor inputProcessor) {
        int oldTitleIndex = titleSelectionIndex;
        if (pressKeyPlease != null) {
            inputVector.y = 0;
        }
        titleSelectionIndex = titleSelectionIndex - (int) inputVector.y;
        if (inputVector.y != 0) {
            titleLock = true;
            caenMain.lockMovement(true);
        }
        List<String> menuOptions = titleOptions;
        if (isOptionsMenu) {
            menuOptions = optionOptions;
        }
        if (isCreditsMenu) {
            menuOptions = creditOptions;
        }
        if (titleSelectionIndex > menuOptions.size() - 1) {
            titleSelectionIndex = menuOptions.size() - 1;
        }
        if (titleSelectionIndex < 0) {
            titleSelectionIndex = 0;
        }
        if (isTitleMenu) {
            if (!hasContinue) {
                if (titleSelectionIndex < 1) {
                    titleSelectionIndex = 1;
                }
            }
        }
        if (oldTitleIndex != titleSelectionIndex) {
            soundPlayer.playSound("music/select-2.ogg", caenMain.getPlayerPos());
        }
        if (pressKeyPlease != null && inputProcessor.hasInput) {
            inputProcessor.setNewKey(pressKeyPlease);
            titleLock = true;
            inputVector.x = 0;
            pressKeyPlease = null;
            return;
        }
        if (inputVector.x != 0 || Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.ENTER) || inputProcessor.pressingA) {
            soundPlayer.playSound(BLIP_SELECT_ITEM_SOUND_ID, "music/select-3.ogg", caenMain.getPlayerPos(), false);
            if (isTitleMenu) {
                if (titleSelectionIndex == 0) {
                    isTitleMenu = false;
                    //loadLevelFromPrefs();
                    titleLock = true;
                    caenMain.lockMovement(true);
                    soundPlayer.resumeSounds();
                    caenMain.resumeGame();
                }
                if (titleSelectionIndex == 1) {
                    if (hasContinue) {
                        showSaveWarning = true;
                        caenMain.startDialog("saveWarning", new DialogVerb("new-game"));
                    } else {
                        soundPlayer.resumeSounds();
                        //gotoState("new-game");
                        caenMain.startOpeningScene();
                        return;
                    }
                }
                if (titleSelectionIndex == 2) {
                    isOptionsMenu = true;
                    isTitleMenu = false;
                    titleLock = true;
                    caenMain.lockMovement(true);
                    titleSelectionIndex = optionOptions.size() - 1;
                }
                if (titleSelectionIndex == 3) {
                    // show credits
                    isCreditsMenu = true;
                    isTitleMenu = false;
                    titleLock = true;
                    caenMain.lockMovement(true);
                    titleSelectionIndex = 0;
                }
            } else {
                if (isCreditsMenu) {
                    isTitleMenu = true;
                    isCreditsMenu = false;
                    titleLock = true;
                    caenMain.lockMovement(true);
                    titleSelectionIndex = 3;
                } else {
                    if (isOptionsMenu) {
                        int tempIndex = optionOptions.size() - 1 - titleSelectionIndex;
                        if (inputProcessor.keyMappings.containsKey(optionOptions.get(tempIndex))) {
                            if (inputVector.x > 0) {
                                titleLock = true;
                                if (pressKeyPlease == null) {
                                    pressKeyPlease = optionOptions.get(tempIndex);
                                    inputProcessor.acceptInput();
                                } else {
                                    pressKeyPlease = null;
                                }
                            }
                        }
                        if (optionOptions.get(tempIndex).equals("sound")) {
                            // sound volume
                            if (inputVector.x > 0) {
                                soundPlayer.increaseSoundVolume();
                            }
                            if (inputVector.x < 0) {
                                soundPlayer.decreaseSoundVolume();
                            }
                        }
                        if (optionOptions.get(tempIndex).equals("music")) {
                            // music volume
                            if (inputVector.x > 0) {
                                soundPlayer.increaseMusicVolume();
                            }
                            if (inputVector.x < 0) {
                                soundPlayer.decreaseMusicVolume();
                            }
                        }
                        if (optionOptions.get(tempIndex).equals("reset everything!")) {
                            Preferences prefs = Gdx.app.getPreferences("caen-preferences");
                            prefs.clear();
                            prefs.flush();
                            isTitleMenu = true;
                            isOptionsMenu = false;
                            titleLock = true;
                            caenMain.lockMovement(true);
                            titleSelectionIndex = 2;
                            caenMain.loadEverything();
                        }
                        if (optionOptions.get(tempIndex).equals("brightness")) {
                            // brightness
                            float gamma = caenMain.getGamma();
                            if (inputVector.x > 0) {
                                gamma += 0.01f;
                            }
                            if (inputVector.x < 0) {
                                gamma -= 0.01f;
                            }
                            caenMain.setGamma(MathUtils.clamp(gamma, 0, 1.0f));
                        }
                        if (optionOptions.get(tempIndex).equals("back")) {
                            // go back
                            isTitleMenu = true;
                            isOptionsMenu = false;
                            titleLock = true;
                            caenMain.lockMovement(true);
                            titleSelectionIndex = 2;
                            caenMain.saveEverything();
                        }
                    }
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (inputVector.x == 0 && inputVector.y == 0) {
            titleLock = false;
        }
        inputVector = new Vector2();
        return;
    }

    public void setHasContinue(boolean hasContinue) {
        this.hasContinue = hasContinue;
    }
}
