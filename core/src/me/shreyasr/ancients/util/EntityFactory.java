package me.shreyasr.ancients.util;

import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.minlog.Log;
import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.*;
import me.shreyasr.ancients.component.attack.AnimatedAttack;
import me.shreyasr.ancients.component.attack.WeaponAnimation;
import me.shreyasr.ancients.component.attack.WeaponData;
import me.shreyasr.ancients.game.GamePlayer;
import me.shreyasr.ancients.game.PlayerData;

import java.io.Serializable;
import java.util.function.Predicate;

public class EntityFactory {
    
    // @formatter:off
    public static GamePlayer createGamePlayer(int id, Datafile data) {
        Log.info("entityfactory", data.toString());
        GamePlayer gamePlayer = new GamePlayer(id,
            new PlayerData(id, Asset.PLAYER, new Rectangle(8 - 32, 8 - 32, 48, 48),
                data.getInt("knockbackDuration"), data.getInt("knockbackDistance"), data.getInt("animFrameTimeMillis"),
                new DirAnim(0,
                    new DirAnim.Frame(0, 16),
                    new DirAnim.Frame(16, 16),
                    new DirAnim.Frame(32, 16),
                    new DirAnim.Frame(48, 16)),
                new DirAnim(0,
                    new DirAnim.Frame(0, 0),
                    new DirAnim.Frame(16, 0),
                    new DirAnim.Frame(32, 0),
                    new DirAnim.Frame(48, 0)),
                new DirAnim(0,
                    new DirAnim.Frame(0, 32),
                    new DirAnim.Frame(16, 32),
                    new DirAnim.Frame(32, 32),
                    new DirAnim.Frame(48, 32)),
                new DirAnim(0,
                    new DirAnim.Frame(0, 48),
                    new DirAnim.Frame(16, 48),
                    new DirAnim.Frame(32, 48),
                    new DirAnim.Frame(48, 48))
            ),
            new Pos(100, 100),
            GamePlayer.Status.ALIVE,
            new TexTransform(16, 16, 4),
            new DirectionalAnimation(),
            new Hitbox(),
            new WeaponHitbox(new CircleSlice(0, 0, 0)),
            new AnimatedAttack(),
            new Knockback(new Pos(0, 0)),
            new PlayerStats(5)
        );
        
        CircleSlice swordHitbox = new CircleSlice(40, 90, 45);
        gamePlayer.data.weapons.add(new WeaponData()
            .addAttackDirectionWithPredicate((Predicate<Pos> & Serializable) mousePos -> mousePos.x >= 0 && mousePos.y >= 0,
                new Attack.AnimFrame(WeaponAnimation.SWORD, 0, 50, swordHitbox, 0),
                new Attack.AnimFrame(WeaponAnimation.SWORD, 1, 50, swordHitbox, 45),
                new Attack.AnimFrame(WeaponAnimation.SWORD, 2, 150, swordHitbox, 90),
                new Attack.AnimFrame(WeaponAnimation.SWORD, -1, 800, swordHitbox, 0))
            .addAttackDirectionWithPredicate((Predicate<Pos> & Serializable) mousePos -> mousePos.x <= 0 && mousePos.y >= 0,
                new Attack.AnimFrame(WeaponAnimation.SWORD, 2, 50, swordHitbox, 90),
                new Attack.AnimFrame(WeaponAnimation.SWORD, 3, 50, swordHitbox, 135),
                new Attack.AnimFrame(WeaponAnimation.SWORD, 4, 150, swordHitbox, 180),
                new Attack.AnimFrame(WeaponAnimation.SWORD, -1, 800, swordHitbox, 0))
            .addAttackDirectionWithPredicate((Predicate<Pos> & Serializable) mousePos -> mousePos.x <= 0 && mousePos.y <= 0,
                new Attack.AnimFrame(WeaponAnimation.SWORD, 4, 50, swordHitbox, 180),
                new Attack.AnimFrame(WeaponAnimation.SWORD, 5, 50, swordHitbox, 225),
                new Attack.AnimFrame(WeaponAnimation.SWORD, 6, 150, swordHitbox, 270),
                new Attack.AnimFrame(WeaponAnimation.SWORD, -1, 800, swordHitbox, 0))
            .addAttackDirectionWithPredicate((Predicate<Pos> & Serializable) mousePos -> mousePos.x >= 0 && mousePos.y <= 0,
                new Attack.AnimFrame(WeaponAnimation.SWORD, 6, 50, swordHitbox, 270),
                new Attack.AnimFrame(WeaponAnimation.SWORD, 7, 50, swordHitbox, 315),
                new Attack.AnimFrame(WeaponAnimation.SWORD, 0, 150, swordHitbox, 360),
                new Attack.AnimFrame(WeaponAnimation.SWORD, -1, 800, swordHitbox, 0)));
    
        CircleSlice daggerHitbox = new CircleSlice(40, 70, 35);
        gamePlayer.data.weapons.add(new WeaponData()
            .addAttackDirectionWithPredicate(
                (Predicate<Pos> & Serializable) mousePos -> mousePos.x >= mousePos.y && mousePos.x >= -mousePos.y,
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 15, 30, daggerHitbox, -22.5f),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 0, 30, daggerHitbox, 0),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 1, 40, daggerHitbox, 22.5f),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, -1, 250, daggerHitbox, 0))
            .addAttackDirectionWithPredicate(
                (Predicate<Pos> & Serializable) mousePos -> mousePos.x <= mousePos.y && mousePos.x >= -mousePos.y,
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 3, 30, daggerHitbox, 90-22.5f),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 4, 30, daggerHitbox, 90),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 5, 40, daggerHitbox, 90+22.5f),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, -1, 250, daggerHitbox, 0))
            .addAttackDirectionWithPredicate(
                (Predicate<Pos> & Serializable) mousePos -> mousePos.x <= mousePos.y && mousePos.x <= -mousePos.y,
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 7, 30, daggerHitbox, 180-22.5f),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 8, 30, daggerHitbox, 180),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 9, 40, daggerHitbox, 180+22.5f),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, -1, 250, daggerHitbox, 0))
            .addAttackDirectionWithPredicate(
                (Predicate<Pos> & Serializable) mousePos -> mousePos.x >= mousePos.y && mousePos.x <= -mousePos.y,
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 11, 30, daggerHitbox, 270-22.5f),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 12, 30, daggerHitbox, 270),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, 13, 40, daggerHitbox, 270+22.5f),
                new Attack.AnimFrame(WeaponAnimation.DAGGER, -1, 250, daggerHitbox, 0)));
    
        CircleSlice spearHitbox = new CircleSlice(40, 90, 25);
        CircleSlice spearHitboxExtended = new CircleSlice(40, 180, 25);
        gamePlayer.data.weapons.add(new WeaponData()
            .addAttackDirectionWithPredicate(
                (Predicate<Pos> & Serializable) mousePos -> mousePos.x >= mousePos.y && mousePos.x >= -mousePos.y,
                new Attack.AnimFrame(WeaponAnimation.SPEAR, 0, 60, spearHitbox, 0),
                new Attack.AnimFrame(WeaponAnimation.SPEAR, 1, 100, spearHitboxExtended, 0),
                new Attack.AnimFrame(WeaponAnimation.SPEAR, -1, 1200, null, 0))
            .addAttackDirectionWithPredicate(
                (Predicate<Pos> & Serializable) mousePos -> mousePos.x <= mousePos.y && mousePos.x >= -mousePos.y,
                new Attack.AnimFrame(WeaponAnimation.SPEAR, 2, 60, spearHitbox, 90),
                new Attack.AnimFrame(WeaponAnimation.SPEAR, 3, 100, spearHitboxExtended, 90),
                new Attack.AnimFrame(WeaponAnimation.SPEAR, -1, 1200, null, 90))
            .addAttackDirectionWithPredicate(
                (Predicate<Pos> & Serializable) mousePos -> mousePos.x <= mousePos.y && mousePos.x <= -mousePos.y,
                new Attack.AnimFrame(WeaponAnimation.SPEAR, 4, 60, spearHitbox, 180),
                new Attack.AnimFrame(WeaponAnimation.SPEAR, 5, 100, spearHitboxExtended, 180),
                new Attack.AnimFrame(WeaponAnimation.SPEAR, -1, 1200, null, 180))
            .addAttackDirectionWithPredicate(
                (Predicate<Pos> & Serializable) mousePos -> mousePos.x >= mousePos.y && mousePos.x <= -mousePos.y,
                new Attack.AnimFrame(WeaponAnimation.SPEAR, 6, 30, spearHitbox, 270),
                new Attack.AnimFrame(WeaponAnimation.SPEAR, 7, 100, spearHitboxExtended, 270),
                new Attack.AnimFrame(WeaponAnimation.SPEAR, -1, 1200, null, 270)));
        return gamePlayer;
        // @formatter:on
    }
}
