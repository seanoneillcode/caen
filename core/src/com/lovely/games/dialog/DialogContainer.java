package com.lovely.games.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.SoundPlayer;

import java.util.*;

import static com.lovely.games.dialog.DialogLine.line;
import static com.lovely.games.dialog.DialogLine.options;

public class DialogContainer {

    public static final float NORMAL_FACE_SCALE = 0.6f;
    public static final float SMALL_FACE_SCALE = 0.5f;
    public static final float SCALE_INCREASE_AMOUNT = 0.01f;
    private final Sprite rightDialogPointer;
    public Map<String, List<DialogElement>> dialogs = new HashMap<>();
    String pro = "pro";
    String ant = "ant";
    String info = "info";
    private Sprite leftPortrait, rightPortrait;
    private String lastAntMood;
    public Vector2 lastPos = new Vector2();

    {
        dialogs.put("1", Arrays.asList(
                line(pro, "Perhaps I can catch him")
        ));
        dialogs.put("2", Arrays.asList(
                line(info, "( press 'R' to restart a level )")
        ));
        dialogs.put("3", Arrays.asList(
                line(pro, "I've never seen a flying ball of fire before. Magic!", "happy"),
                line(pro, "I better not let it touch me though", "worried")
        ));
        dialogs.put("4", Arrays.asList(
                line(pro, "( press 'R' to restart a level )")
        ));
        dialogs.put("5", Arrays.asList(
                line(pro, "There it is. The magic stone.")
        ));
        dialogs.put("7", Arrays.asList(
                line(pro, "Damn!", "angry"),
                line(pro, "Now I can't leave", "angry")
        ));
        dialogs.put("8", Arrays.asList(
                line(pro, "What does it say..."),
                line(info, "\"Power requires sacrifice.\"")
        ));
        dialogs.put("9", Arrays.asList(
                line(ant, "Wretched fool! I'll blast you to ashes!", "angry")
        ));
        dialogs.put("10", Arrays.asList(
                line(ant, "A wise decision, and an end to this trial.", "happy"),
                line(ant, "Leave and tell no one what happened here."),
                line(pro, "Have no fear of that. I will be dead in a few days from the hunger.", "angry"),
                line(ant, "You might be a rogue, but I will not let you go hungry today"),
                line(ant, "Take these coins and find something else to steal another day")
        ));
        dialogs.put("13", Arrays.asList(
                line(pro, "My bones feel like they're going to burst", "worried"),
                line(info, "(press 'spacebar' to cast a spell)")
        ));
        dialogs.put("14", Arrays.asList(
                line(ant, "Yes?"),
                options(pro)
                        .opt("Is this the Caen?", "18")
                        .opt("Who are you?", "19")
                        .opt("What should I do?", "20")
                        .build()
        ));
        dialogs.put("15", Arrays.asList(
                line(ant, "No!", "angry"),
                line(ant, "No! You bastard!", "angry"),
                line(ant, "I'll kill you!", "angry")
        ));
        dialogs.put("16", Arrays.asList(
                line(ant, "Thank you for your kindness.", "happy"),
                line(ant, "Fool, now I'll take what's mine.", "happy")
        ));
        dialogs.put("18", Arrays.asList(
                line(pro, "At last, the diamond! I'm rich", "happy")
        ));
        dialogs.put("19", Arrays.asList(
                line(ant, "Yes?"),
                options(pro)
                        .opt("When will we arrive?", "18")
                        .opt("Why do I have to carry everything...", "19")
                        .opt("Where are we going?", "20")
                        .mood("worried")
                        .build()
        ));
        dialogs.put("24", Arrays.asList(
                line(pro, "I thought I killed you..."),
                line(ant, "You did, I just need a little rest before I move on", "happy"),
                line(ant, "Tell me, can you still sell the stone, knowing it's power?", "talk"),
                line(pro, "I don't know")
        ));
        dialogs.put("saveWarning", Arrays.asList(
                line(info, "There is already a save game. Starting a new game will overwrite the save game."),
                line(info, "Do you wish to start a new game?"),
                options(info)
                        .opt("yes", "new-game")
                        .opt("no", "menu")
                        .build()
        ));
        dialogs.put("20", Arrays.asList(
                options(info)
                        .opt("sleep", "17")
                        .opt("stay up a little longer", null)
                        .build()
        ));
        dialogs.put("30", Arrays.asList(
                line(pro, "I must be quick! If he catches me I'm dead...", "worried")
        ));
        dialogs.put("31", Arrays.asList(
                line(ant, "You can't escape with the crystal", "angry"),
                line(ant, "The only exit is through me", "talk"),
                line(ant, "Give me the Crystal and I'll let you pass", "happy"),
                options(pro)
                        .opt("Take the damned crystal and let me out", "46")
                        .opt("No, the crystal is mine now", "47")
                        .build()        ));
        dialogs.put("32", Arrays.asList(
                line(ant, "NOOOOoooooo.....!!")
        ));
        dialogs.put("33", Arrays.asList(
                line(pro, "It's a headstone, inscribed with weird symbols."),
                line(pro, "They... they look like they're moving slowly"),
                line(pro, "Must be a trick of the light")
        ));
        dialogs.put("36", Arrays.asList(
                line(pro, "Looks like there used to be a bridge here"),
                line(pro, "I'll have to go the long way around", "worried")
        ));
        dialogs.put("37", Arrays.asList(
                line(pro, "Ominous...")
        ));
        dialogs.put("38", Arrays.asList(
                line(pro, "I hear something...")
        ));
        dialogs.put("39", Arrays.asList(
                line(pro, "I need to get the diamond and get out"),
                line(pro, "That poison should have killed him...")
        ));
        dialogs.put("40", Arrays.asList(
                line(ant, "Stop! What are you doing!"),
                line(pro, "I need the diamond. To pay for food"),
                line(ant, "It's not a diamond, it's a crystal"),
                line(ant, "A vessel for magic"),
                line(pro, "You're out of your mind", "happy"),
                line(ant, "You cannot steal it, I need it to live!"),
                line(pro, "I'm not a fool, I know how much it's worth", "happy"),
                line(ant, "I'll kill you before I let you have it", "angry"),
                line(pro, "You'll have to catch me first", "angry"),
                line(ant, "You can't escape me!", "angry")
        ));
        dialogs.put("41", Arrays.asList(
                line(pro, "It those weird symbols again"),
                line(pro, "I feel like I know what they mean now")
        ));
        dialogs.put("42", Arrays.asList(
                line(ant, "If you wish to be a wizard, you must pass through the trials at CAEN"),
                line(pro, "What kind of trials?", "worried"),
                line(ant, "The trials of knowing..."),
                line(ant, "...of doing..."),
                line(ant, "and of feeling"),
                line(pro, "that sounds like fun!", "happy"),
                line(ant, "You'll be lucky to survive", "angry")
        ));
        dialogs.put("43", Arrays.asList(
                line(pro, "oh...", "worried")
        ));
        dialogs.put("44", Arrays.asList(
                line(pro, "Knock knock!", "worried")
        ));
        dialogs.put("45", Arrays.asList(
                line(ant, "Who is out in this weather..?")
        ));
        dialogs.put("46", Arrays.asList(
                line(ant, "Who's there?", "angry"),
                line(ant, "What do you want?", "angry"),
                line(pro, "I'm selling cider"),
                line(ant, "Well... I did just run out... and I don't want to go out in this storm", "talk"),
                line(ant, "Come in then and show me what you have", "happy")
        ));
        dialogs.put("47", Arrays.asList(
                line(pro, "Try this, it might be too strong for you though..."),
                line(ant, "We'll see...")
        ));
        dialogs.put("48", Arrays.asList(
                line(pro, "Sorry old man, that was mostly poison", "happy"),
                line(pro, "If the rumors are true, you've guarded the greatest of treasure ever known", "talk"),
                line(pro, "The Crystal of Caen", "talk"),
                line(pro, "I need to find the crystal and get out", "talk"),
                line(pro, "If I can sell the Crystal, I'll never be hungry again", "worried")
        ));
        dialogs.put("49", Arrays.asList(
                line(pro, "Just books, nothing I can sell", "talk")
        ));
        dialogs.put("50", Arrays.asList(
                line(pro, "It's a old book", "talk"),
                line(pro, "Full of symbols and written in a language I can't understand", "worried")
        ));
        dialogs.put("51", Arrays.asList(
                line(ant, "The old man?", "talk"),
                line(ant, "Well, I heard he's hiding a diamond the size of your head...", "talk"),
                line(pro, "A diamond? If I stole that, I'd never be hungry again!", "happy"),
                line(ant, "Stealing it could be very dangerous", "talk"),
                line(pro, "Oh, I'll be fine", "happy"),
                line(pro, "I just have to use my head", "worried")
        ));
    }

    String currentDialog;
    Integer dialogIndex;
    private BitmapFont font;
    private float timer;
    private Texture dialogBottom, dialogTop, dialogLineImg;
    private Color fontColorMain = new Color(6f / 256.0f, 27f  / 256.0f, 46f / 256.0f, 1);
    private Color fontColorHighlighted = fontColorMain;//new Color(110f / 256.0f, 36f / 256.0f, 61f / 256.0f, 1);
    private Color fontColorSecondary = new Color(7.0f / 256.0f, 0.0f  / 256.0f, 7.0f / 256.0f, 1);
    private Map<String, Texture> portraits;
    private Sprite dialogPointer, optionPointer;
    private Set<String> actors;
    private String lastMood;
    private float leftScaleTarget, rightScaleTarget, leftScaleAmount, rightScaleAmount;

    String lastChar;

    public DialogContainer(AssetManager assetManager) {
        currentDialog = null;
        dialogIndex = 0;
        font = loadFonts("fonts/decent.fnt");
        timer = 0;
        this.dialogBottom = assetManager.get("dialog-bottom.png");
        this.dialogTop = assetManager.get("dialog-top.png");
        this.dialogLineImg = assetManager.get("dialog-line.png");
        this.dialogPointer = new Sprite((Texture) assetManager.get("dialog-pointer.png"));
        this.rightDialogPointer = new Sprite((Texture) assetManager.get("dialog-pointer.png"));
        this.optionPointer = new Sprite((Texture) assetManager.get("option-pointer.png"));
        rightDialogPointer.flip(true, false);
        portraits = new HashMap<>();
        portraits.put("pro-talk", assetManager.get("portraits/portrait-pro.png"));
        portraits.put("pro-listening", assetManager.get("portraits/portrait-pro-listening.png"));
        portraits.put("pro-angry", assetManager.get("portraits/portrait-pro-angry.png"));
        portraits.put("pro-happy", assetManager.get("portraits/portrait-pro-happy.png"));
        portraits.put("pro-worried", assetManager.get("portraits/portrait-pro-worried.png"));
        portraits.put("ant-talk", assetManager.get("portraits/portrait-ant-talking.png"));
        portraits.put("ant-listening", assetManager.get("portraits/portrait-ant-listening.png"));
        portraits.put("ant-angry", assetManager.get("portraits/portrait-ant-angry.png"));
        portraits.put("ant-happy", assetManager.get("portraits/portrait-ant-happy.png"));
        portraits.put("ant-worried", assetManager.get("portraits/portrait-ant-angry.png"));
        this.leftPortrait = new Sprite(portraits.get("pro-talk"));
        this.rightPortrait = new Sprite(portraits.get("ant-talk"));
        this.rightPortrait.flip(true, false);
        leftScaleTarget = NORMAL_FACE_SCALE;
        rightScaleTarget = NORMAL_FACE_SCALE;
        leftScaleAmount = NORMAL_FACE_SCALE;
        rightScaleAmount = NORMAL_FACE_SCALE;
        leftPortrait.setScale(leftScaleTarget);
        rightPortrait.setScale(rightScaleTarget);
        lastMood = "pro-listening";
        lastAntMood = "ant-listening";
    }

    public void render(SpriteBatch batch, Vector2 offset, Conversation conversation, SoundPlayer soundPlayer) {
        Vector2 dialogPos = offset.cpy().add(64, 16);
        DialogElement dialogLine = conversation.getCurrentDialog();
        actors = new HashSet<>(conversation.getActors());
        float portraitHeight = 170;
        boolean isLeft = dialogLine.getOwner().equals("pro");
        if (isLeft) {
            if (dialogLine.getMood() != null) {
                leftPortrait.setTexture(portraits.get("pro-" + dialogLine.getMood()));
                lastMood = "pro-" + dialogLine.getMood();
            } else {
                leftPortrait.setTexture(portraits.get("pro-talk"));
                lastMood = "pro-listening";
            }
            dialogPointer.setPosition(dialogPos.x + 130, dialogPos.y + portraitHeight - 40);
            rightPortrait.setTexture(portraits.get(lastAntMood));
            leftScaleTarget = NORMAL_FACE_SCALE;
            rightScaleTarget = SMALL_FACE_SCALE;
        } else {
            if (dialogLine.getMood() != null) {
                rightPortrait.setTexture(portraits.get("ant-" + dialogLine.getMood()));
                lastAntMood = "ant-" + dialogLine.getMood();
            } else {
                rightPortrait.setTexture(portraits.get("ant-talk"));
                lastAntMood = "ant-listening";
            }
            leftPortrait.setTexture(portraits.get(lastMood));
            rightDialogPointer.setPosition(dialogPos.x + 252, dialogPos.y + portraitHeight - 40);
            rightScaleTarget = NORMAL_FACE_SCALE;
            leftScaleTarget = SMALL_FACE_SCALE;
        }
        if (leftScaleAmount < leftScaleTarget) {
            leftScaleAmount += SCALE_INCREASE_AMOUNT;
        }
        if (leftScaleAmount > leftScaleTarget) {
            leftScaleAmount -= SCALE_INCREASE_AMOUNT;
        }
        if (rightScaleAmount < rightScaleTarget) {
            rightScaleAmount += SCALE_INCREASE_AMOUNT;
        }
        if (rightScaleAmount > rightScaleTarget) {
            rightScaleAmount -= SCALE_INCREASE_AMOUNT;
        }
        leftPortrait.setScale(leftScaleAmount);
        rightPortrait.setScale(rightScaleAmount);
        if (actors.contains("pro")) {
            leftPortrait.setPosition(dialogPos.x - 20, dialogPos.y + 4 - 100 + 40);
            leftPortrait.draw(batch);
        }
        if (actors.contains("ant")) {
            rightPortrait.setPosition(dialogPos.x + 242, dialogPos.y + 4 - 170 + 40);
            rightPortrait.draw(batch);
        }

        List<String> lines = dialogLine.getLines();
        float ypos = dialogLine.getTotalLines() * 32;

        float startHeight = portraitHeight + 16;
        if (!isLeft) {
            dialogPos.x = dialogPos.x + 40;
        }
        batch.draw(dialogBottom, dialogPos.x, dialogPos.y + startHeight);
        batch.draw(dialogTop, dialogPos.x, dialogPos.y + 8 + ypos + startHeight);
        for (int i = 0; i < dialogLine.getTotalLines(); i++) {
            batch.draw(dialogLineImg, dialogPos.x, dialogPos.y + 8 + (i * 32) + startHeight);
        }

        if (!dialogLine.getOwner().equals("info")) {
            if (isLeft) {
                dialogPointer.draw(batch);
            } else {
                rightDialogPointer.draw(batch);
            }
        }

        float offsetx = 0;
        for (String line : lines) {
            String chosenOption = dialogLine.getCurrentOption();
            offsetx = dialogLine instanceof DialogOption ? 16 : 0;
            if (chosenOption != null && line.equals(chosenOption)) {
                optionPointer.setPosition(dialogPos.x + 6, dialogPos.y - 2 + ypos + startHeight - 14);
                optionPointer.draw(batch);
                font.setColor(fontColorHighlighted);
                font.draw(batch, line, dialogPos.x + 6 + offsetx, dialogPos.y + ypos + startHeight - 2);
            } else {
                font.setColor(fontColorMain);
                font.draw(batch, line, dialogPos.x + 6 + offsetx, dialogPos.y + ypos + startHeight - 2);
            }

            ypos = ypos - 32;
        }


        lastPos.x = dialogPos.x + 390;
        lastPos.y = dialogPos.y + startHeight - 28;

        String lastLine = lines.get(lines.size() - 1);
        if (!lastLine.isEmpty()) {
            String thisChar = lastLine.substring(lastLine.length() - 1);
            if (!thisChar.isEmpty() && (lastChar == null || !thisChar.equals(lastChar))) {
                if (!thisChar.equals(" ")) {
                    if (isLeft) {
                        soundPlayer.playSound("music/talk-high-beep.ogg");
                    } else {
                        soundPlayer.playSound("music/talk-shift.ogg");
                    }
                    lastChar = thisChar;
                }
            }
        }

    }

    private BitmapFont loadFonts(String fontString) {
        font = new BitmapFont(Gdx.files.internal(fontString),false);
        font.setUseIntegerPositions(false);
        font.setColor(fontColorMain);
        return font;
    }

    public void reset() {
        lastMood = "pro-listening";
    }

    public boolean isAtEndOfSentence() {
        return false;
    }
}
