package me.shreyasr.ancients.util;

import com.badlogic.gdx.math.Rectangle;
import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.*;
import me.shreyasr.ancients.component.attack.SwordAttack;
import me.shreyasr.ancients.component.attack.WeaponAnimation;
import me.shreyasr.ancients.game.GamePlayer;

public class EntityFactory {
    
    public static GamePlayer createGamePlayer(int id) {
        CircleSlice swordHitbox = new CircleSlice(40, 90, 45);
        return new GamePlayer(id, Asset.PLAYER,
                new Pos(100, 100),
                new TexTransform(16, 16, 4),
                new DirectionalAnimation(200,
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
                                new DirAnim.Frame(48, 48))),
                new Hitbox(new Rectangle(8 - 32, 8 - 32, 48, 48)),
                new WeaponHitbox(new CircleSlice(40, 90, 60)),
                new SwordAttack(
                        new Attack.AnimFrame(WeaponAnimation.SWORD, 0, 400, swordHitbox, 0),
                        new Attack.AnimFrame(WeaponAnimation.SWORD, 1, 400, swordHitbox, 45),
                        new Attack.AnimFrame(WeaponAnimation.SWORD, 2, 700, swordHitbox, 90),
                        new Attack.AnimFrame(WeaponAnimation.SWORD, -1, 500, swordHitbox, 0))
        );
    }
}
