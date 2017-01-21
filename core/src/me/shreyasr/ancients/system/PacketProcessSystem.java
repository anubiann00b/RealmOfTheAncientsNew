package me.shreyasr.ancients.system;

import com.badlogic.ashley.core.EntitySystem;
import me.shreyasr.ancients.GameScreen;
import me.shreyasr.ancients.component.PosComponent;
import me.shreyasr.ancients.network.InputPacket;

public class PacketProcessSystem extends EntitySystem {

    private GameScreen game;

    public PacketProcessSystem(GameScreen game, int priority) {
        super(priority);
        this.game = game;
    }

    @Override
    public void update(float deltaTime) {
        InputPacket packet = game.packetQueue.popPacket();

        PosComponent pos = game.player.getComponent(PosComponent.class);
        if (packet.a.isKeyDown()) pos.x -= 10;
        if (packet.d.isKeyDown()) pos.x += 10;
        if (packet.w.isKeyDown()) pos.y += 10;
        if (packet.s.isKeyDown()) pos.y -= 10;
    }
}
