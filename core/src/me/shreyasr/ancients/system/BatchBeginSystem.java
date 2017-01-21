package me.shreyasr.ancients.system;

import com.badlogic.ashley.core.EntitySystem;
import me.shreyasr.ancients.GameScreen;

public class BatchBeginSystem extends EntitySystem {

    private GameScreen game;

    public BatchBeginSystem(GameScreen game, int priority) {
        super(priority);
        this.game = game;
    }

    @Override
    public void update(float deltaTime) {
        game.batch.begin();
    }
}
