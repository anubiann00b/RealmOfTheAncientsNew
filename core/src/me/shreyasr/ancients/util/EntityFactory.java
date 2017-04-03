package me.shreyasr.ancients.util;

import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.DirAnim;
import me.shreyasr.ancients.component.DirectionalAnimation;
import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.component.TexTransform;
import me.shreyasr.ancients.game.GamePlayer;

public class EntityFactory {
    
    public static GamePlayer createGamePlayer(int id) {
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
                                new DirAnim.Frame(48, 48))));
    }
}
