package me.shreyasr.ancients.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Input;
import me.shreyasr.ancients.GameScreen;
import me.shreyasr.ancients.network.InputPacket;
import me.shreyasr.ancients.util.AccumulatingInputProcessor;

public class InputSystem extends EntitySystem {

    public AccumulatingInputProcessor input;

    private GameScreen game;

    public InputSystem(GameScreen game, int priority) {
        super(priority);
        this.game = game;
        input = new AccumulatingInputProcessor(Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D);
    }

    @Override
    public void update(float deltaTime) {
        InputPacket packet = new InputPacket(
                input.get(Input.Keys.W),
                input.get(Input.Keys.A),
                input.get(Input.Keys.S),
                input.get(Input.Keys.D));
        game.packetQueue.addPacket(packet);

        input.update();
    }
}
