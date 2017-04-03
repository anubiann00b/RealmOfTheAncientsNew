package me.shreyasr.ancients;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.esotericsoftware.kryonet.Client;
import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.component.TexTransform;
import me.shreyasr.ancients.game.GamePlayer;
import me.shreyasr.ancients.game.GameState;
import me.shreyasr.ancients.network.CustomPacketListener;
import me.shreyasr.ancients.network.InputData;
import me.shreyasr.ancients.util.AccumulatingInputProcessor;
import me.shreyasr.ancients.util.GameStateQueue;
import me.shreyasr.ancients.util.MathHelper;

public class GameScreen extends ScreenAdapter {
    
    public int id;
    
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    public final SpriteBatch batch;
    public final ShapeRenderer shape;

    public final OrthographicCamera camera = new OrthographicCamera(640, 480);
    public final ExtendViewport viewport = new ExtendViewport(800, 600, 1280, 720, camera);

    public final GameStateQueue gameStateQueue = new GameStateQueue();
    public final Client client;
    
    private AccumulatingInputProcessor input = new AccumulatingInputProcessor(Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D);
    private OrthogonalTiledMapRenderer renderer;

    private boolean initialized = false;

    public GameScreen(SpriteBatch batch, ShapeRenderer shape, Client client) {
        this.batch = batch;
        this.shape = shape;
        this.client = client;
        client.addListener(new CustomPacketListener()
                .doOnConnect(conn -> id = conn.getID())
                .doOnGameState((conn, gameState) -> gameStateQueue.put(gameState)));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        renderer = new OrthogonalTiledMapRenderer(Asset.MAP.getMap(), 4f, batch);
        inputMultiplexer.addProcessor(input);
        initialized = true;
    }
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        if (!initialized) {
            return;
        }
    
        InputData inputData = new InputData(
                input.get(Input.Keys.W),
                input.get(Input.Keys.A),
                input.get(Input.Keys.S),
                input.get(Input.Keys.D));
        client.sendUDP(inputData);
        
        GameState gameStateToDraw = gameStateQueue.getInterpolatedCurrentState(System.currentTimeMillis());
        
        Pos playerPos;
        GamePlayer myPlayer = gameStateToDraw.players.getById(id);
        if (myPlayer != null) {
            playerPos = myPlayer.pos;
        } else {
            playerPos = new Pos(0, 0);
        }
    
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.position.set(
                MathHelper.clamp(viewport.getWorldWidth()/2,  playerPos.x, 3840- viewport.getWorldWidth()/2),
                MathHelper.clamp(viewport.getWorldHeight()/2, playerPos.y, 3840- viewport.getWorldHeight() /2),
                0);
        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    
        renderer.setView(camera);
        renderer.render();
    
        batch.begin();
        
        for (GamePlayer player : gameStateToDraw.players) {
            TexTransform ttc = player.ttc;
            
            if (ttc.hide) return;
            
            ttc.srcX = player.animation.getSrcX();
            ttc.srcY = player.animation.getSrcY();
    
            System.out.println(player.animation);
    
            Texture texture = player.asset.getTex();
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    
            batch.setColor(ttc.color);
            batch.draw(texture, player.pos.x - ttc.originX, player.pos.y - ttc.originY, ttc.originX, ttc.originY,
                    ttc.screenWidth, ttc.screenHeight, 1, 1, ttc.rotation,
                    ttc.srcX, ttc.srcY, ttc.srcWidth, ttc.srcHeight, false, false);
        }
    
        batch.end();
    }

}
