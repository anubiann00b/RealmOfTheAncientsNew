package me.shreyasr.ancients;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import me.shreyasr.ancients.component.Attack;
import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.component.TexTransform;
import me.shreyasr.ancients.game.GamePlayer;
import me.shreyasr.ancients.game.GameState;
import me.shreyasr.ancients.network.CustomPacketListener;
import me.shreyasr.ancients.network.InputData;
import me.shreyasr.ancients.util.AccumulatingInputProcessor;
import me.shreyasr.ancients.util.GameStateQueue;
import me.shreyasr.ancients.util.InterceptingKryoSerialization;
import me.shreyasr.ancients.util.Utils;

import java.io.IOException;

public class GameScreen extends ScreenAdapter {
    
    public int id;
    
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    public final SpriteBatch batch;
    public final ShapeRenderer shape;
    private final BitmapFont font = new BitmapFont();

    public final OrthographicCamera camera = new OrthographicCamera(640, 480);
    public final ExtendViewport viewport = new ExtendViewport(800, 600, 1280, 720, camera);

    public final GameStateQueue gameStateQueue = new GameStateQueue();
    public final Client client;
    
    private AccumulatingInputProcessor input = new AccumulatingInputProcessor(Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D, Input.Keys.R);
    private OrthogonalTiledMapRenderer renderer;

    private boolean initialized = false;

    public GameScreen(SpriteBatch batch, ShapeRenderer shape, Client client) {
        this.batch = batch;
        this.shape = shape;
        this.client = client;
        client.addListener(new CustomPacketListener()
                .doOnConnect(conn -> id = conn.getID())
                .doOnGameState((conn, gameState) -> gameStateQueue.put(gameState))
                .doOnPlayerData((conn, playerData) -> gameStateQueue.put(playerData.playerId, playerData))
        );
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        renderer = new OrthogonalTiledMapRenderer(Asset.MAP.getMap(), 4f, batch);
        inputMultiplexer.addProcessor(input);
        try {
            client.connect(2000, "127.0.0.1", 54555, 54777);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        Vector2 mousePosInWorld = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        InputData inputData = new InputData(
                new Pos(mousePosInWorld.x, mousePosInWorld.y),
                input.get(Input.Keys.W),
                input.get(Input.Keys.A),
                input.get(Input.Keys.S),
                input.get(Input.Keys.D),
                Gdx.input.isButtonPressed(Input.Buttons.LEFT),
                Gdx.input.isButtonPressed(Input.Buttons.RIGHT),
                input.get(Input.Keys.R));
        client.sendUDP(inputData);
        
        GameState gameStateToDraw = gameStateQueue.getInterpolatedCurrentState(System.currentTimeMillis());
        
        Pos playerPos = gameStateToDraw.players.getByIdOpt(id)
                .map(p -> p.pos)
                .orElse(new Pos(0, 0));
    
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.position.set(
                Utils.clamp(viewport.getWorldWidth()/2,  playerPos.x, 3840- viewport.getWorldWidth()/2),
                Utils.clamp(viewport.getWorldHeight()/2, playerPos.y, 3840- viewport.getWorldHeight()/2),
                0);
        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        
        renderer.setView(camera);
        renderer.render();
    
        batch.begin();
        
        for (GamePlayer player : gameStateToDraw.players) {
            if (player.data != null) {
                player.currentAttack.applyFrame(player.data, player.weaponHitbox);
                TexTransform ttc = player.ttc;
    
                if (player.knockback.isInKnockback() && (int)(player.knockback.percentDone(player.data) * 4) % 2 == 0) {
                    ttc.color = Color.RED;
                } else {
                    ttc.color = Color.WHITE;
                }
    
                if (ttc.hide) return;
    
                ttc.srcX = player.animation.getSrcX(player.data);
                ttc.srcY = player.animation.getSrcY(player.data);
    
                Texture texture = player.data.asset.getTex();
                texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    
                batch.setColor(ttc.color);
                batch.draw(texture, player.pos.x - ttc.originX, player.pos.y - ttc.originY, ttc.originX, ttc.originY,
                        ttc.screenWidth, ttc.screenHeight, 1, 1, ttc.rotation,
                        ttc.srcX, ttc.srcY, ttc.srcWidth, ttc.srcHeight, false, false);
    
                Attack.AnimFrame frame = player.currentAttack.getCurrentAnimFrame(player.data);
                if (frame != null && frame.frameNumber != -1) {
                    Texture weaponTex = frame.animation.asset.getTex();
                    weaponTex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        
                    int frameSize = frame.animation.frameSize;
                    int frameNumber = frame.frameNumber;
                    batch.setColor(Color.WHITE);
                    batch.draw(weaponTex, player.pos.x - frameSize * 4 / 2, player.pos.y - frameSize * 4 / 2,
                            frameSize * 4, frameSize * 4,
                            frameSize * frameNumber, 0, frameSize, frameSize, false, false);
                }
            } else {
                Log.debug("gamescreen", "No PlayerData for player " + player.id + ": " + player);
            }
        }
    
        batch.setColor(Color.WHITE);
        InterceptingKryoSerialization serialization = ((InterceptingKryoSerialization)client.getSerialization());
        font.draw(batch, "Read rate: " + Utils.humanReadableBitCount(serialization.getReadByteCount() * 8), 8, Gdx.graphics.getHeight()-8-32);
        font.draw(batch, "Write rate: " + Utils.humanReadableBitCount(serialization.getWrittenByteCount() * 8), 8, Gdx.graphics.getHeight()-8-16);
        font.draw(batch, "Packet overhead: " + Utils.humanReadableBitCount(32*8 * 60), 8, Gdx.graphics.getHeight()-8);
    
        batch.end();
        
        shape.begin();
    
        for (GamePlayer player : gameStateToDraw.players) {
            if (player.data != null) {
                Rectangle rect = player.hitbox.getRect(player.data, player.pos);
                if (player.hitbox.isBeingHit) {
                    shape.set(ShapeRenderer.ShapeType.Filled);
                    shape.setColor(Color.RED);
                } else {
                    shape.set(ShapeRenderer.ShapeType.Line);
                    shape.setColor(Color.WHITE);
                }
                shape.rect(rect.x, rect.y, rect.width, rect.height);
            }
            
            if (player.weaponHitbox.active) {
                shape.set(ShapeRenderer.ShapeType.Line);
                shape.setColor(Color.WHITE);
                player.weaponHitbox.cs.draw(shape, player.pos);
            }
            
            shape.set(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.RED);
            shape.rect(player.pos.x - 32, player.pos.y - 40, 64, 8);
            shape.setColor(Color.GREEN);
            shape.rect(player.pos.x - 32, player.pos.y - 40, 64*((float)player.stats.currentHealth/player.stats.maxHealth), 8);
        }
        
        shape.end();
    }
}
