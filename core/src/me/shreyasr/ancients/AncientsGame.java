package me.shreyasr.ancients;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AncientsGame extends Game {
    
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private AssetManager assetManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);

        assetManager = new AssetManager();
        Asset.loadAll(assetManager);
        assetManager.finishLoading();

        setScreen(new GameScreen(batch, shape));
    }

    @Override
    public void render() {
        super.render();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shape.dispose();
        assetManager.dispose();
    }
}
