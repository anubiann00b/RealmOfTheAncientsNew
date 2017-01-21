package me.shreyasr.ancients.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.GameScreen;

public class MapRenderSystem extends EntitySystem {

    private GameScreen game;
    private OrthogonalTiledMapRenderer renderer;

    public MapRenderSystem(GameScreen game, int priority) {
        super(priority);
        this.game = game;
        renderer = new OrthogonalTiledMapRenderer(Asset.MAP.getMap(), 4f, game.batch);
    }

    @Override
    public void update(float deltaTime) {
        renderer.setView(game.camera);
        renderer.render();
    }
}
