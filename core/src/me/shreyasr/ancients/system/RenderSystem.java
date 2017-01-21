package me.shreyasr.ancients.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import me.shreyasr.ancients.GameScreen;
import me.shreyasr.ancients.component.PosComponent;
import me.shreyasr.ancients.component.TexComponent;
import me.shreyasr.ancients.component.TexTransformComponent;

public class RenderSystem extends IteratingSystem {

    private GameScreen game;

    public RenderSystem(GameScreen game, int priority) {
        super(Family.all(PosComponent.class, TexComponent.class, TexTransformComponent.class).get(), priority);
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PosComponent pos = entity.getComponent(PosComponent.class);
        TexTransformComponent ttc = entity.getComponent(TexTransformComponent.class);
        TexComponent tex = entity.getComponent(TexComponent.class);

        if (ttc.hide) return;

        Texture texture = tex.texture;
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        game.batch.setColor(ttc.color);
        game.batch.draw(texture, pos.x - ttc.originX, pos.y - ttc.originY, ttc.originX, ttc.originY,
                ttc.screenWidth, ttc.screenHeight, 1, 1, ttc.rotation,
                ttc.srcX, ttc.srcY, ttc.srcWidth, ttc.srcHeight, false, false);
    }
}
