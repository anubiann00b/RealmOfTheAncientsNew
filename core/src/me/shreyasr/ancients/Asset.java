package me.shreyasr.ancients;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public enum Asset {

    PLAYER       (Texture.class, "player.png"),
    SWORD_SLASH  (Texture.class, "attacks/sword_slash.png"),
    DAGGER_SLASH (Texture.class, "attacks/dagger_slash.png"),
    AXE_CLEAVE   (Texture.class, "attacks/axe_cleave.png"),
    SPEAR_STAB   (Texture.class, "attacks/spear_stab.png"),

    JOYSTICK_CENTER (Texture.class, "joystick/joystick_center.png"),
    JOYSTICK_BG     (Texture.class, "joystick/joystick_bg.png"),

    MAP (TiledMap.class, "maps/map.tmx");

    private final Class<?> type;
    private final String file;

    public String getFile() {
        return file;
    }

    Asset(Class type, String file) {
        this.type = type;
        this.file = file;
    }

    private static AssetManager assetManager;

    public static void loadAll(AssetManager assetManager) {
        Asset.assetManager = assetManager;
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        for (Asset asset : Asset.values()) {
            assetManager.load(asset.getFile(), asset.type);
        }
    }

    public Texture getTex() {
        return assetManager.get(file);
    }

    public TiledMap getMap() {
        return assetManager.get(file);
    }
}
