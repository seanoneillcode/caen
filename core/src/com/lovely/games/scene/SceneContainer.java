package com.lovely.games.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.lovely.games.Actor;
import com.lovely.games.scene.verbs.*;

import java.util.HashMap;
import java.util.Map;

import static com.lovely.games.scene.Scene.builder;

public class SceneContainer {

    public Map<String, Scene> scenes;

    public SceneContainer() {
        scenes = new HashMap<>();

        // ant tells player to stand on switch
        scenes.put("1", builder()
                .verb(new com.lovely.games.scene.verbs.FadeScreenVerb(true, 2.0f, Color.BLACK))
//                .verb(new MoveVerb(new Vector2(0, 3 * 32), "ant"))
//                .verb(new DialogVerb("6"))
//                .verb(new MoveVerb(new Vector2(4 * 32, 0), "ant", true))
//                .verb(new MoveVerb(new Vector2(0, 32), "ant", true))
                .build());

        // player steps on swithc, closes them off
        scenes.put("2", builder()
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(200,340)))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(0.5f))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("a"))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(0.5f))
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(200,96)))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(0.5f))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("b"))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("7"))
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(200,340)))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 32), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(-4 * 32, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 3 * 32), "ant", true))
                .build());

        // reads the waring stone in start
        scenes.put("3", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("8"))
                .build());

        // at start, tells player not to follow
        scenes.put("4", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("9"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 3 * 32), "ant", true))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(true, "ant"))
                .build());

        // catching up with ant
        scenes.put("5", builder()
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(7 * 32, 32 * 8)))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("10"))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(0.5f))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(6 * 32, 0), "ant", false))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(true, "ant"))
                .build());

        scenes.put("7", builder()
                .resetCamera(false)
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("c"))
                .build());

        // ant fucks over player
        scenes.put("6", builder()
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(11 * 32, 32 * 4)))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(2f))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(3 * 32, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(2f))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("11"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, -5 * 32), "ant", false))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(-5 * 32, 0), "ant", false))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, -3 * 32), "ant", false))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(true, "ant"))
                .build());

        // old man at magic stone
        scenes.put("8", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("12"))
                .build());

        scenes.put("9", builder()
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 32 * -2), "ant", true))
                .build());

        // ant asks for help
        scenes.put("10", builder()
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(3 * 32, 32 * 8)))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(false, "ant"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(2 * 32, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 32 * 5), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(2 * 32, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("14"))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("b"))
                .build());

        // pro feels the magic
        scenes.put("11", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("11"))
                .build());

        // pro walks away to leave ant to die
        scenes.put("12", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("15"))
                .build());

        // pro is a kind fool!
        scenes.put("13", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("16"))
                .verb(new com.lovely.games.scene.verbs.PosterVerb("poster-help-ant.png"))
                .verb(new com.lovely.games.scene.verbs.FadeScreenVerb(false, 2.0f, Color.WHITE))
                .build());

        scenes.put("14", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("18"))
                .verb(new com.lovely.games.scene.verbs.PosterVerb("posters/poster-prize.png"))
                .verb(new com.lovely.games.scene.verbs.HideShowImageVerb(true, "entity/crystal.png"))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("a"))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("c"))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("13"))
                .build());

        scenes.put("15", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("14"))
                .build());

        // standing in fire
        scenes.put("16", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("20"))
                .build());
        scenes.put("18", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("21"))
                .build());
        scenes.put("19", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("22"))
                .build());
        scenes.put("20", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("23"))
                .build());

        // go to sleep
        scenes.put("17", builder()
                .verb(new com.lovely.games.scene.verbs.FadeScreenVerb(false, 2.0f, Color.BLACK))
                .verb(new com.lovely.games.scene.verbs.FadeScreenVerb(true, 0.1f, Color.BLACK))
                .verb(new com.lovely.games.scene.verbs.PosterVerb("posters/ending-poster.png"))
                .verb(new com.lovely.games.scene.verbs.FadeScreenVerb(false, 0.1f, Color.BLACK))
                .build());

        scenes.put("21", builder()
                .verb(new com.lovely.games.scene.verbs.FadeScreenVerb(true, 2.0f, Color.BLACK))
                .build());
        scenes.put("22", builder()
                .verb(new com.lovely.games.scene.verbs.FadeScreenVerb(true, 0.2f, Color.BLACK))
                .build());

        scenes.put("23", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("24"))
                .build());

        scenes.put("24", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("25"))
                .build());

        scenes.put("25", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("26"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 32 * 2), "ant", true))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(true, "ant"))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("a"))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("27"))
                .build());

        scenes.put("26", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("28"))
                .build());

        scenes.put("27", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("29"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 32 * -2), "pro", true))
                .build());

        scenes.put("28", builder()
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(-6 * 32, 0), "pro", true))
//                .verb(new com.lovely.games.scene.verbs.DialogVerb("31"))
//                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(true, "ant"))
//                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(false, "boss"))
                .verb(new SetAntPhaseVerb(Actor.Phase.DISAPPEAR))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("a"))
                .build());

        scenes.put("29", builder()
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(false, "ant"))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(true, "boss"))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("32"))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("b"))
                .build());

        scenes.put("30", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("33"))
                .build());

        scenes.put("31", builder()
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(true, "pro"))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(0.5f))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("b"))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(0.25f))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32 * 2, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("34"))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(0.5f))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("35"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32 * -1, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 32 * 6), "ant", true))
                .verb(new com.lovely.games.scene.verbs.ConnectionVerb("33"))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(false, "pro"))
                .build());

        scenes.put("32", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("36"))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(0.5f))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("a"))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(1f))
                .verb(new ConnectionVerb("209"))
                .build());

        scenes.put("33", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("36"))
                .build());

        scenes.put("34", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("38"))
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(290, 96)))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(false, "ant"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32 * 4, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(1f))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32 * 4, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(true, "ant"))
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(290, 430)))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("39"))
                .build());

        scenes.put("35", builder()
                .verb(new com.lovely.games.scene.verbs.SetAntAnimVerb("fall", 0 , true))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(0.5f))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("a"))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("38"))
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(290, 96)))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(false, "ant"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32 * 4, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 32), "ant", true))
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(290, 240)))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(1f))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("40"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, -32), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32 * 4, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(true, "ant"))
//                .verb(new CameraVerb(new Vector2(290, 430)))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("39"))
                .build());

        scenes.put("36", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("41"))
                .verb(new com.lovely.games.scene.verbs.WaitVerb(0.5f))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("a"))
                .build());

        scenes.put("37", builder()
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("a"))
                .verb(new com.lovely.games.scene.verbs.HideShowActorVerb(false, "ant"))
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(544, 1184)))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 32), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32 * -2, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, -32 * 4), "ant", true))
                .build());

        scenes.put("38", builder()
                .verb(new HideShowActorVerb(false, "ant"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 3 * -32), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32 * 3, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, -32), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32 * 2, 0), "ant", true))
                .build());

        scenes.put("39", builder()
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("b"))
                .build());

        scenes.put("40", builder()
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("b"))
                .verb(new com.lovely.games.scene.verbs.SendEventVerb("c"))
                .build());

        scenes.put("42", builder()
                .verb(new com.lovely.games.scene.verbs.PosterVerb("new-game-scene"))
                .build());

        scenes.put("43", builder()
                .verb(new WaitVerb(1f))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, -16), "pro", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(-32, 0), "pro", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, -80), "pro", true))
//                .verb(new DialogVerb("44"))
                .verb(new com.lovely.games.scene.verbs.CameraVerb(new Vector2(200, 240)))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("45"))
                .verb(new CameraVerb(new Vector2(200, 340)))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32 * 2, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, 32 * 4), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(-32 * 2, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("46"))
                .verb(new HideShowImageVerb(true, "entity/heavy-door.png"))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(32, -32), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(-4, 0), "ant", true))
                .verb(new com.lovely.games.scene.verbs.MoveVerb(new Vector2(0, -80), "pro", true))
                .verb(new MoveVerb(new Vector2(16, -32), "pro", true))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("47"))
                .verb(new PlaySoundVerb("sound/the-visitor.ogg", new Vector2(200, 240)))
                .verb(new com.lovely.games.scene.verbs.SetAntAnimVerb("drink", 0.2f * 14, true))
                .verb(new SetAntAnimVerb("fall", 0.1f * 9 , false))
                .verb(new com.lovely.games.scene.verbs.DialogVerb("48"))
                .verb(new SendEventVerb("a"))
                .build());

        scenes.put("44", builder()
                .verb(new com.lovely.games.scene.verbs.DialogVerb("49"))
                .build());

        scenes.put("45", builder()
                .verb(new DialogVerb("50"))
                .build());

        scenes.put("new-game", builder()
                .verb(new com.lovely.games.scene.verbs.GameControlVerb("new-game"))
                .build());

        scenes.put("menu", builder()
                .verb(new GameControlVerb("menu"))
                .build());

        scenes.put("poster-test", builder()
                .verb(new com.lovely.games.scene.verbs.FadeScreenVerb(false, 2.0f, Color.BLACK))
                .verb(new com.lovely.games.scene.verbs.FadeScreenVerb(true, 0.1f, Color.BLACK))
                .verb(new PosterVerb("posters/poster-prize.png"))
                .verb(new FadeScreenVerb(false, 0.1f, Color.BLACK))
                .build());
    }

}
