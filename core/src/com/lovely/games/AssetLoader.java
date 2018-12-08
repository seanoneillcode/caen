package com.lovely.games;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class AssetLoader {

    public AssetManager getLoadedAssetManager() {
        AssetManager assetManager = new AssetManager(new InternalFileHandleResolver());

        FileHandleResolver fileHandleResolver = new InternalFileHandleResolver();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(fileHandleResolver));
        assetManager.setLoader(Texture.class, new TextureLoader(fileHandleResolver));
        assetManager.setLoader(Music.class, new MusicLoader(fileHandleResolver));
        assetManager.clear();

        assetManager.load("levels/tower-01.tmx", TiledMap.class);
        assetManager.load("levels/tower-02.tmx", TiledMap.class);
        assetManager.load("levels/tower-arrow-01.tmx", TiledMap.class);
        assetManager.load("levels/tower-arrow-02.tmx", TiledMap.class);
        assetManager.load("levels/tower-arrow-03.tmx", TiledMap.class);
        assetManager.load("levels/tower-arrow-04.tmx", TiledMap.class);
        assetManager.load("levels/tower-arrow-06.tmx", TiledMap.class);
        assetManager.load("levels/tower-platform-01.tmx", TiledMap.class);
        assetManager.load("levels/tower-platform-02.tmx", TiledMap.class);
        assetManager.load("levels/tower-platform-03.tmx", TiledMap.class);
        assetManager.load("levels/tower-platform-04.tmx", TiledMap.class);
        assetManager.load("levels/tower-block-01.tmx", TiledMap.class);
        assetManager.load("levels/tower-block-02.tmx", TiledMap.class);
        assetManager.load("levels/tower-block-03.tmx", TiledMap.class);
        assetManager.load("levels/tower-block-04.tmx", TiledMap.class);
        assetManager.load("levels/tower-switch-01.tmx", TiledMap.class);
        assetManager.load("levels/tower-switch-02.tmx", TiledMap.class);
        assetManager.load("levels/tower-switch-03.tmx", TiledMap.class);
        assetManager.load("levels/tower-arrow-05.tmx", TiledMap.class);
        assetManager.load("levels/tower-switch-04.tmx", TiledMap.class);
        assetManager.load("levels/tower-switch-05.tmx", TiledMap.class);
        assetManager.load("levels/last-room.tmx", TiledMap.class);
        assetManager.load("levels/scene-test.tmx", TiledMap.class);
        assetManager.load("levels/tower-broken-level.tmx", TiledMap.class);
        assetManager.load("levels/tower-bridge-1.tmx", TiledMap.class);
        assetManager.load("levels/tower-prize-fight.tmx", TiledMap.class);
        assetManager.load("levels/tower-ant-revenge.tmx", TiledMap.class);
        assetManager.load("levels/camp-fire.tmx", TiledMap.class);
        assetManager.load("levels/bullet-01.tmx", TiledMap.class);
        assetManager.load("levels/bullet-02.tmx", TiledMap.class);
        assetManager.load("levels/bullet-03.tmx", TiledMap.class);
        assetManager.load("levels/bullet-04.tmx", TiledMap.class);
        assetManager.load("levels/bullet-05.tmx", TiledMap.class);
        assetManager.load("levels/bullet-06.tmx", TiledMap.class);
        assetManager.load("levels/bullet-07.tmx", TiledMap.class);
        assetManager.load("levels/maze-1.tmx", TiledMap.class);
        assetManager.load("levels/enemy-1.tmx", TiledMap.class);
        assetManager.load("levels/enemy-2.tmx", TiledMap.class);
        assetManager.load("levels/enemy-3.tmx", TiledMap.class);
        assetManager.load("levels/enemy-4.tmx", TiledMap.class);
        assetManager.load("levels/enemy-5.tmx", TiledMap.class);
        assetManager.load("levels/enemy-6.tmx", TiledMap.class);
        assetManager.load("levels/enemy-8.tmx", TiledMap.class);
        assetManager.load("levels/enemy-9.tmx", TiledMap.class);
        assetManager.load("levels/crossy-road-1.tmx", TiledMap.class);
        assetManager.load("levels/crossy-road-2.tmx", TiledMap.class);
        assetManager.load("levels/entrance-2.tmx", TiledMap.class);
        assetManager.load("levels/stairs-1.tmx", TiledMap.class);
        assetManager.load("levels/boss-fight.tmx", TiledMap.class);
        assetManager.load("levels/options.tmx", TiledMap.class);
        assetManager.load("levels/lobby-2.tmx", TiledMap.class);
        assetManager.load("levels/gate-1.tmx", TiledMap.class);
        assetManager.load("levels/gate-2.tmx", TiledMap.class);
        assetManager.load("levels/gate-3.tmx", TiledMap.class);
        assetManager.load("levels/ant-catch-up.tmx", TiledMap.class);
        assetManager.load("levels/trench.tmx", TiledMap.class);
        assetManager.load("levels/trailer-level.tmx", TiledMap.class);
        assetManager.update();

        assetManager.load("entity/platform.png", Texture.class);
        assetManager.load("entity/platform-anim.png", Texture.class);
        assetManager.load("entity/platform-particle-1.png", Texture.class);
        assetManager.load("entity/block.png", Texture.class);
        assetManager.load("entity/pressure.png", Texture.class);
        assetManager.load("entity/ground-block.png", Texture.class);
        assetManager.load("entity/arrow-explode.png", Texture.class);
        assetManager.load("entity/arrow-sheet.png", Texture.class);
        assetManager.load("entity/arrow-source.png", Texture.class);
        assetManager.load("entity/torch-anim.png", Texture.class);
        assetManager.load("entity/enemy.png", Texture.class);
        assetManager.load("entity/enemy-idle.png", Texture.class);
        assetManager.load("entity/enemy-idle-hor.png", Texture.class);
        assetManager.load("entity/enemy-shoot-hor.png", Texture.class);
        assetManager.load("entity/enemy-shoot.png", Texture.class);
        assetManager.load("entity/enemy-ground.png", Texture.class);
        assetManager.load("entity/lazer.png", Texture.class);
        assetManager.load("entity/lazer-horizontal.png", Texture.class);
        assetManager.load("entity/campfire.png", Texture.class);
        assetManager.load("entity/grass-1.png", Texture.class);
        assetManager.load("entity/grass-2.png", Texture.class);
        assetManager.load("entity/grass-3.png", Texture.class);
        assetManager.load("entity/grass-4.png", Texture.class);
        assetManager.load("entity/dust-air.png", Texture.class);
        assetManager.load("entity/dust-air-2.png", Texture.class);
        assetManager.load("entity/lintel.png", Texture.class);
        assetManager.load("entity/heavy-door.png", Texture.class);
        assetManager.load("entity/crystal.png", Texture.class);
        assetManager.load("character/dead.png", Texture.class);
        assetManager.load("entity/rain.png", Texture.class);
        assetManager.load("entity/door-open.png", Texture.class);
        assetManager.load("levels/door-horizontal.png", Texture.class);
        assetManager.load("levels/door-dust.png", Texture.class);
        assetManager.load("levels/door-vertical.png", Texture.class);
        assetManager.load("entity/pressure-on.png", Texture.class);
        assetManager.load("entity/switch-on.png", Texture.class);
        assetManager.update();

        assetManager.load("character/pro-simple-fall-death.png", Texture.class);
        assetManager.load("character/pro-simple-fire-death.png", Texture.class);
        assetManager.load("character/pro-simple-push.png", Texture.class);
        assetManager.load("character/pro-simple-shoot.png", Texture.class);
        assetManager.load("character/pro-simple-idle.png", Texture.class);
        assetManager.load("character/pro-simple-walk.png", Texture.class);
        assetManager.load("character/pro-simple-walk-down.png", Texture.class);
        assetManager.load("character/pro-simple-walk-up.png", Texture.class);
        assetManager.load("character/ant-idle.png", Texture.class);
        assetManager.load("character/ant-walk.png", Texture.class);
        assetManager.load("character/ant-prepare.png", Texture.class);
        assetManager.load("character/ant-appear.png", Texture.class);
        assetManager.load("character/ant-simple-fall.png", Texture.class);
        assetManager.load("character/ant-simple-drink.png", Texture.class);
        assetManager.load("character/player-shadow.png", Texture.class);
        assetManager.update();

        assetManager.load("portraits/portrait-pro.png", Texture.class);
        assetManager.load("portraits/portrait-pro-listening.png", Texture.class);
        assetManager.load("portraits/portrait-pro-angry.png", Texture.class);
        assetManager.load("portraits/portrait-pro-happy.png", Texture.class);
        assetManager.load("portraits/portrait-pro-worried.png", Texture.class);
        assetManager.load("portraits/portrait-ant-talking.png", Texture.class);
        assetManager.load("portraits/portrait-ant-listening.png", Texture.class);
        assetManager.load("portraits/portrait-ant-angry.png", Texture.class);
        assetManager.load("portraits/portrait-ant-happy.png", Texture.class);
        assetManager.update();

        assetManager.load("posters/ending-poster.png", Texture.class);
        assetManager.load("posters/poster-prize.png", Texture.class);
        assetManager.load("posters/stone-0.png", Texture.class);
        assetManager.load("posters/stone-1.png", Texture.class);
        assetManager.load("posters/stone-2.png", Texture.class);
        assetManager.load("posters/stone-3.png", Texture.class);
        assetManager.load("posters/menu-sprites.png", Texture.class);
        assetManager.update();

        assetManager.load("dialog-bottom.png", Texture.class);
        assetManager.load("dialog-top.png", Texture.class);
        assetManager.load("dialog-line.png", Texture.class);
        assetManager.load("light-hole.png", Texture.class);
        assetManager.load("light-magic.png", Texture.class);
        assetManager.load("player-light.png", Texture.class);
        assetManager.load("level-light.png", Texture.class);
        assetManager.load("fade-image.png", Texture.class);
        assetManager.load("dialog-pointer.png", Texture.class);
        assetManager.load("caen-title.png", Texture.class);
        assetManager.load("posters/title-behind.png", Texture.class);
        assetManager.load("posters/title-front.png", Texture.class);
        assetManager.load("option-pointer.png", Texture.class);
        assetManager.load("volume-pointer.png", Texture.class);
        assetManager.load("volume-level-on.png", Texture.class);
        assetManager.load("volume-level-off.png", Texture.class);
        assetManager.load("player-large.png", Texture.class);
        assetManager.load("select-arrow.png", Texture.class);
        assetManager.load("pointer.png", Texture.class);
        assetManager.update();

        assetManager.load("music/arrow-source.ogg", Music.class);
        assetManager.load("music/blast-1.ogg", Music.class);
        assetManager.load("music/block-3.ogg", Music.class);
        assetManager.load("music/flame-0.ogg", Music.class);
        assetManager.load("music/lazer-4.ogg", Music.class);
        assetManager.load("music/new-game-1.ogg", Music.class);
        assetManager.load("music/scream-hurt.ogg", Music.class);
        assetManager.load("music/select-2.ogg", Music.class);
        assetManager.load("music/step-2.ogg", Music.class);
        assetManager.load("music/switch-1.ogg", Music.class);
        assetManager.load("music/talk-beep.ogg", Music.class);
        assetManager.load("music/talk-high-beep.ogg", Music.class);
        assetManager.load("music/talk-shift.ogg", Music.class);
        assetManager.load("music/thunk.ogg", Music.class);
        assetManager.load("music/door.ogg", Music.class);
        assetManager.load("music/platform-4.ogg", Music.class);
        assetManager.load("music/select-3.ogg", Music.class);
        assetManager.finishLoading();

        return assetManager;
    }
}
