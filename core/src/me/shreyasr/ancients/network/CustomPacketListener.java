package me.shreyasr.ancients.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import me.shreyasr.ancients.game.GameState;
import me.shreyasr.ancients.game.PlayerData;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CustomPacketListener implements Listener {
    
    private Consumer<Connection> doOnConnect = c -> {};
    private Consumer<Connection> doOnDisconnect = c -> {};
    private BiConsumer<Connection, InputData> inputDataConsumer = (c, p) -> {};
    private BiConsumer<Connection, GameState> gameStateConsumer = (c, p) -> {};
    private BiConsumer<Connection, PlayerData> playerDataConsumer = (c, p) -> {};
    
    public CustomPacketListener doOnConnect(Consumer<Connection> doOnConnect) {
        this.doOnConnect = doOnConnect;
        return this;
    }
    
    public CustomPacketListener doOnDisconnect(Consumer<Connection> doOnDisconnect) {
        this.doOnDisconnect = doOnDisconnect;
        return this;
    }
    
    public CustomPacketListener doOnInputData(BiConsumer<Connection, InputData> inputDataConsumer) {
        this.inputDataConsumer = inputDataConsumer;
        return this;
    }
    
    public CustomPacketListener doOnGameState(BiConsumer<Connection, GameState> gameStateConsumer) {
        this.gameStateConsumer = gameStateConsumer;
        return this;
    }
    
    public CustomPacketListener doOnPlayerData(BiConsumer<Connection, PlayerData> playerDataConsumer) {
        this.playerDataConsumer = playerDataConsumer;
        return this;
    }
    
    @Override
    public void connected(Connection conn) {
        Log.info("connection-listener", "Connected to: " + conn.getRemoteAddressUDP() + ", ID: " + conn.getID());
        doOnConnect.accept(conn);
    }
    
    @Override
    public void disconnected(Connection conn) {
        Log.info("connection-listener", "Disconnected: " + conn.getID());
        doOnDisconnect.accept(conn);
    }
    
    @Override
    public void idle(Connection conn) {
        Log.trace("connection-listener", "Idle: " + conn.getRemoteAddressUDP() + ", ID: " + conn.getID());
    }
    
    @Override
    public void received(Connection conn, Object object) {
        Log.debug("connection-listener", "Received packet: " + object.toString());
        if (inputDataConsumer != null && object instanceof InputData) {
            inputDataConsumer.accept(conn, (InputData) object);
        }
        if (gameStateConsumer != null && object instanceof GameState) {
            gameStateConsumer.accept(conn, (GameState) object);
        }
        if (playerDataConsumer != null && object instanceof PlayerData) {
            playerDataConsumer.accept(conn, (PlayerData) object);
        }
    }
}
