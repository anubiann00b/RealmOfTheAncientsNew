package me.shreyasr.ancients.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.KryoSerialization;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import me.shreyasr.ancients.game.GamePlayer;
import me.shreyasr.ancients.game.GameState;
import me.shreyasr.ancients.network.CustomPacketListener;
import me.shreyasr.ancients.util.EntityFactory;
import me.shreyasr.ancients.util.InputDataQueue;
import me.shreyasr.ancients.util.KryoRegistrar;
import me.shreyasr.ancients.util.Utils;

import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    
    private static Server server;
    
    private static GameState currentGameState = new GameState(System.currentTimeMillis() - 16);
    private static int millisWithNoPackets = 0;
    private static InputDataQueue inputDataQueue = new InputDataQueue();
    private static final List<Integer> idsToRemove = new ArrayList<>();

    public static void main(String[] args) {
        Log.set(Log.LEVEL_INFO);
        Gdx.files = new LwjglFiles();
    
        try {
            runServer();
        } catch (IOException e) {
            Log.error("server", e.getMessage(), e);
        } finally {
            if (server != null) server.close();
        }
    }
    
    private static void runServer() throws IOException {
        server = new Server(16384, 4096, new KryoSerialization(KryoRegistrar.makeKryo()));
        KryoRegistrar.register(server.getKryo());
        server.start();
    
        try {
            server.bind(54555, 54777);
        } catch (BindException e) {
            if (e.getMessage().equals("Address already in use: bind")) {
                Log.error("server", "Port in use, failed to start");
                e.printStackTrace();
                server.close();
                System.exit(0);
            } else {
                throw e;
            }
        }
    
        server.addListener(new CustomPacketListener()
                .doOnConnect(conn -> {
                    for (GamePlayer player : currentGameState.players) {
                        conn.sendTCP(player.data);
                    }
                })
                .doOnInputData((conn, inputData) -> {
                    inputDataQueue.putInputData(conn.getID(), inputData, System.currentTimeMillis());
                    millisWithNoPackets = 0;
                })
                .doOnDisconnect(conn -> { synchronized (idsToRemove) { idsToRemove.add(conn.getID()); } }));
    
        while(true) {
            long processTime = System.currentTimeMillis();
        
            // Remove disconnected players
            synchronized (idsToRemove) {
                for (int id : idsToRemove) {
                    currentGameState.players.remove(id);
                }
                idsToRemove.clear();
            }
        
            // Create new players for new connections
            for (Connection conn : server.getConnections()) {
                int id = conn.getID();
                if (conn.isConnected() && id != -1) {
                    GamePlayer player = currentGameState.players.getById(id);
                    if (player == null) {
                        player = EntityFactory.createGamePlayer(id, Utils.readDataFile(Gdx.files.internal("data/data.ini")));
                        currentGameState.players.put(id, player);
                        server.sendToAllTCP(player.data);
                    }
                }
            }
        
            for (GamePlayer player : currentGameState.players) {
                player.lastInput = player.input;
                player.input = inputDataQueue.getNextInput(player.id, processTime);
            }
        
            currentGameState = new GameState(currentGameState);
            currentGameState.update(16);
        
            server.sendToAllUDP(currentGameState);
        
            millisWithNoPackets += 16;
            if (millisWithNoPackets > 120 * 1000) {
                server.close();
                System.exit(0);
            }
        
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}