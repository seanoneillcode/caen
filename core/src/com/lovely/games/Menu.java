package com.lovely.games;

import java.util.Arrays;
import java.util.List;

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



}
