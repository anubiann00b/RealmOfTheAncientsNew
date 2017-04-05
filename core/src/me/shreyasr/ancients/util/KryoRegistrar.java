package me.shreyasr.ancients.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.kryo.Kryo;
import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.*;
import me.shreyasr.ancients.component.attack.InstantAttack;
import me.shreyasr.ancients.component.attack.SwordAttack;
import me.shreyasr.ancients.component.attack.WeaponAnimation;
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
        kryo.register(WeaponHitbox.class);
        kryo.register(CircleSlice.class);
        kryo.register(Rectangle.class);
        kryo.register(Hitbox.class);
        kryo.register(Attack.class);
        kryo.register(Attack.AnimFrame.class);
        kryo.register(Attack.AnimFrame[].class);
        kryo.register(WeaponAnimation.class);
        kryo.register(InstantAttack.class);
        kryo.register(SwordAttack.class);
        kryo.register(Color.class);
        kryo.register(HashMap.class);
    }
}
