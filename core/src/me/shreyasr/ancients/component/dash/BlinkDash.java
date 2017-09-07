package me.shreyasr.ancients.component.dash;

import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.game.PlayerData;
import me.shreyasr.ancients.network.InputData;

public class BlinkDash extends Dash {
    
    @Override
    public Dash copy() {
        BlinkDash dash = new BlinkDash();
        return dash;
    }
    
    @Override
    public void cancel() {
        
    }
    
    @Override
    public void beginDash(PlayerData playerData, int deltaMillis, Pos pos, InputData input) {
        float mouseDir = input.mousePos.sub(pos).getDirRadians();
        pos.x += 50 * Math.cos(mouseDir);
        pos.y += 50 * Math.sin(mouseDir);
    }
    
    @Override
    public void update(PlayerData data, int deltaMillis, Pos pos) {
        
    }
}
