package me.shreyasr.ancients.util;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.*;
import me.shreyasr.ancients.game.GamePlayer;
import me.shreyasr.ancients.game.GameState;
import me.shreyasr.ancients.game.PlayerSet;
import me.shreyasr.ancients.network.InputData;

import java.util.HashMap;

public class KryoRegistrar {

    public static void register(Kryo kryo) {
        kryo.register(InputData.class);
        kryo.register(GameState.class);
        kryo.register(PlayerSet.class);
        kryo.register(GamePlayer.class);
        kryo.register(Asset.class);
        kryo.register(TexTransform.class);
        kryo.register(Pos.class);
        kryo.register(Animation.class);
        kryo.register(DirectionalAnimation.class);
        kryo.register(DirAnim.class);
        kryo.register(DirAnim.Frame.class);
        kryo.register(DirAnim.Frame[].class);
        kryo.register(Color.class);
        kryo.register(HashMap.class);
    }
}
