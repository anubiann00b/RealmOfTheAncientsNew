package me.shreyasr.ancients.game;

import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.DirectionalAnimation;
import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.component.TexTransform;
import me.shreyasr.ancients.network.InputData;

public class GamePlayer {
    
    public transient InputData input;
    
    public int id;
    
    public Asset asset;
    public TexTransform ttc;
    public Pos pos;
    public Pos vel = new Pos(0, 0);
    public DirectionalAnimation animation;
    
    public GamePlayer() { }
    
    public GamePlayer(int id, Asset asset, Pos pos, TexTransform ttc, DirectionalAnimation animation) {
        this.id = id;
        this.asset = asset;
        this.pos = pos;
        this.ttc = ttc;
        this.animation = animation;
    }
    
    public GamePlayer(GamePlayer other) {
        this(other.id, other.asset, new Pos(other.pos), new TexTransform(other.ttc), new DirectionalAnimation(other.animation));
        this.input = other.input;
    }
    
    public void update(int deltaMillis) {
        if (input.d) vel.x = 5;
        if (input.a) vel.x = -5;
        if (input.w) vel.y = 5;
        if (input.s) vel.y = -5;
        
        animation.update(deltaMillis, vel.x != 0 || vel.y != 0, vel.getDirDegrees());
        
        pos.x += vel.x;
        pos.y += vel.y;
    }
    
    public void interpolateTo(GamePlayer nextPlayer, float percentageToNext) {
        pos.x = pos.x * (1 - percentageToNext) + nextPlayer.pos.x * percentageToNext;
        pos.y = pos.y * (1 - percentageToNext) + nextPlayer.pos.y * percentageToNext;
    }
    
    @Override
    public String toString() {
        return "GamePlayer{" +
                "id=" + id +
                ", asset=" + asset +
                ", ttc=" + ttc +
                ", pos=" + pos +
                '}';
    }
}
