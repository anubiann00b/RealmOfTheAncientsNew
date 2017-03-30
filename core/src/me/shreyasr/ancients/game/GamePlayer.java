package me.shreyasr.ancients.game;

import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.TexTransformComponent;
import me.shreyasr.ancients.network.InputData;

public class GamePlayer {
    
    public transient InputData input;
    
    public int id;
    
    public Asset asset;
    public TexTransformComponent ttc;
    
    public float x;
    public float y;
    
    public GamePlayer() { }
    
    public GamePlayer(int id, Asset asset, float x, float y, TexTransformComponent ttc) {
        this.id = id;
        this.asset = asset;
        this.x = x;
        this.y = y;
        this.ttc = ttc;
    }
    
    @Override
    public String toString() {
        return "GamePlayer{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
