package me.shreyasr.ancients.component.dash;

import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.game.PlayerData;
import me.shreyasr.ancients.network.InputData;

public class BlinkDash extends Dash {
    
    private final int dashTime;
    private final int dashDistance;
    
    private Pos dashStartPos;
    private Pos dashEndPos;
    private boolean isDashing = false;
    private int dashTimer = -1;
    
    public BlinkDash(int dashTime, int dashDistance) {
        this.dashTime = dashTime;
        this.dashDistance = dashDistance;
    }
    
    @Override
    public Dash copy() {
        BlinkDash dash = new BlinkDash(dashTime, dashDistance);
        dash.dashStartPos = dashStartPos;
        dash.dashEndPos = dashEndPos;
        dash.isDashing = isDashing;
        dash.dashTimer = dashTimer;
        return dash;
    }
    
    @Override
    public boolean isDashing() {
        return isDashing;
    }
    
    @Override
    public void beginDash(PlayerData playerData, int deltaMillis, Pos pos, InputData input) {
        float dashDir = input.mousePos.sub(pos).getDirRadians();
        dashStartPos = new Pos(pos);
        dashEndPos = dashStartPos.add(new Pos(
                (float)(dashDistance*Math.cos(dashDir)),
                (float)(dashDistance*Math.sin(dashDir))));
        dashTimer = 0;
        isDashing = true;
    }
    
    @Override
    public void cancelDash() {
        isDashing = false;
    }
    
    @Override
    public void update(PlayerData data, int deltaMillis, Pos pos) {
        if (isDashing) {
            dashTimer += deltaMillis;
            if (dashTimer > dashTime) {
                isDashing = false;
                dashTimer = dashTime;
            }
            
            float dashPercentage = (float)dashTimer/dashTime;
            Pos currentDashPos = dashStartPos.interpolateTo(dashEndPos, dashPercentage);
            pos.x = currentDashPos.x;
            pos.y = currentDashPos.y;
        }
    }
}
