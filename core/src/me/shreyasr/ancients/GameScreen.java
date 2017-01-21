package me.shreyasr.ancients;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import me.shreyasr.ancients.component.PosComponent;
import me.shreyasr.ancients.component.TexComponent;
import me.shreyasr.ancients.component.TexTransformComponent;
import me.shreyasr.ancients.network.PacketQueue;
import me.shreyasr.ancients.system.*;

public class GameScreen extends ScreenAdapter {


    public final SpriteBatch batch;
    public final ShapeRenderer shape;

    public final Engine engine = new Engine();
    public final Entity player = new Entity();

    public final OrthographicCamera camera = new OrthographicCamera(640, 480);
    public final ExtendViewport viewport = new ExtendViewport(800, 600, 1280, 720, camera);

    public final PacketQueue packetQueue = new PacketQueue();

    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();

    private boolean initialized = false;

    public GameScreen(SpriteBatch batch, ShapeRenderer shape) {
        this.batch = batch;
        this.shape = shape;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);

        player
                .add(new PosComponent(100, 100))
                .add(new TexComponent(Asset.PLAYER))
                .add(new TexTransformComponent(64, 64));

        engine.addEntity(player);

        int priority = 0;

        engine.addSystem(new InputSystem(this, ++priority));
        engine.addSystem(new PacketProcessSystem(this, ++priority));

        engine.addSystem(new ClearFrameSystem(++priority));
        engine.addSystem(new CameraUpdateSystem(this, ++priority));
        engine.addSystem(new MapRenderSystem(this, ++priority));
        engine.addSystem(new BatchBeginSystem(this, ++priority));
        engine.addSystem(new RenderSystem(this, ++priority));
        engine.addSystem(new BatchEndSystem(this, ++priority));

        inputMultiplexer.addProcessor(engine.getSystem(InputSystem.class).input);

        initialized = true;
    }

    @Override
    public void render(float delta) {
        if (initialized) {
            engine.update(Gdx.graphics.getRawDeltaTime() * 1000);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
