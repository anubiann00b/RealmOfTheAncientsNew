package me.shreyasr.ancients.game;

import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.component.TexTransform;
import me.shreyasr.ancients.network.InputData;

public class GamePlayer {
    
    public transient InputData input;
    
    public int id;
    
    public Asset asset;
    public TexTransform ttc;
    public Pos pos;
    
    public GamePlayer() { }
    
    public GamePlayer(int id, Asset asset, Pos pos, TexTransform ttc) {
        this.id = id;
        this.asset = asset;
        this.pos = pos;
        this.ttc = ttc;
    }
    
    public GamePlayer(GamePlayer other) {
        this(other.id, other.asset, new Pos(other.pos), new TexTransform(other.ttc));
        this.input = other.input;
    }
    
    public void update() {
        if (input.d) pos.x += 5;
        if (input.a) pos.x -= 5;
        if (input.w) pos.y += 5;
        if (input.s) pos.y -= 5;
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
