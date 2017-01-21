package me.shreyasr.ancients.system;

import com.badlogic.ashley.core.EntitySystem;
import me.shreyasr.ancients.GameScreen;
import me.shreyasr.ancients.component.PosComponent;
import me.shreyasr.ancients.util.MathHelper;

public class CameraUpdateSystem extends EntitySystem {

    private GameScreen game;

    public CameraUpdateSystem(GameScreen game, int priority) {
        super(priority);
        this.game = game;
    }

    @Override
    public void update(float deltaTime) {
        PosComponent pos = game.player.getComponent(PosComponent.class);
        game.camera.position.set(
                MathHelper.clamp(game.viewport.getWorldWidth()/2,  pos.x, 3840- game.viewport.getWorldWidth()/2),
                MathHelper.clamp(game.viewport.getWorldHeight()/2, pos.y, 3840- game.viewport.getWorldHeight() /2),
                0);
        game.viewport.apply();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
    }
}
