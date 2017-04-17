package me.shreyasr.ancients.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.ClosureSerializer;
import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.*;
import me.shreyasr.ancients.component.attack.AttackDirections;
import me.shreyasr.ancients.component.attack.InstantAttack;
import me.shreyasr.ancients.component.attack.SwordAttack;
import me.shreyasr.ancients.component.attack.WeaponAnimation;
import me.shreyasr.ancients.game.GamePlayer;
import me.shreyasr.ancients.game.GameState;
import me.shreyasr.ancients.game.PlayerData;
import me.shreyasr.ancients.game.PlayerSet;
import me.shreyasr.ancients.network.InputData;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.lang.invoke.SerializedLambda;
import java.util.HashMap;

public class KryoRegistrar {
    
    public static Kryo makeKryo() {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);
    
        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        
        return kryo;
    }

    public static void register(Kryo kryo) {
        kryo.register(Color.class);
        kryo.register(HashMap.class);
        
        kryo.register(Object[].class);
        kryo.register(java.lang.Class.class);
        kryo.register(SerializedLambda.class);
        kryo.register(EntityFactory.class);
        kryo.register(ClosureSerializer.Closure.class, new ClosureSerializer());
        
        kryo.register(InputData.class);
        kryo.register(GameState.class);
        kryo.register(PlayerSet.class);
        kryo.register(GamePlayer.class);
        kryo.register(PlayerData.class);
        kryo.register(Asset.class);
        kryo.register(TexTransform.class);
        kryo.register(Pos.class);
        kryo.register(Animation.class);
        kryo.register(DirectionalAnimation.class);
        kryo.register(DirectionalAnimation.Direction.class);
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
        kryo.register(AttackDirections.class);
        kryo.register(AttackDirections.AttackDirection.class);
        kryo.register(AttackDirections.AttackDirection[].class);
        kryo.register(Knockback.class);
        
        kryo.addDefaultSerializer(Color.class, new Serializer<Color>() {
            @Override
            public void write(Kryo kryo, Output output, Color color) {
                output.writeInt(Color.rgba8888(color));
            }
    
            @Override
            public Color read(Kryo kryo, Input input, Class type) {
                return new Color(input.readInt());
            }
        });
    }
}
