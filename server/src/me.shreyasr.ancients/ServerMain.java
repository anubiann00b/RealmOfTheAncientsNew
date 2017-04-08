package me.shreyasr.ancients;

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

import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        Log.set(Log.LEVEL_INFO);

        InputDataQueue inputDataQueue = new InputDataQueue();

        Server server = new Server(16384, 4096, new KryoSerialization(KryoRegistrar.makeKryo()));
        KryoRegistrar.register(server.getKryo());
        server.start();
    
        try {
            server.bind(54555, 54777);
        } catch (BindException e) {
            if (e.getMessage().equals("Address already in use: bind")) {
                Log.error("server", "Port in use, failed to start");
                server.close();
                System.exit(0);
            } else {
                throw e;
            }
        }
    
        List<Integer> idsToRemove = new ArrayList<>();
    
        final long[] timeDisconnected = {0};

        server.addListener(new CustomPacketListener()
                .doOnInputData((conn, inputData) -> {
                    inputDataQueue.putInputData(conn.getID(), inputData, System.currentTimeMillis());
                    timeDisconnected[0] = 0;
                })
                .doOnDisconnect(conn -> { synchronized (idsToRemove) { idsToRemove.add(conn.getID()); } }));
        
        GameState currentGameState = new GameState(System.currentTimeMillis() - 16);
        
        
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
                        player = EntityFactory.createGamePlayer(id);
                        currentGameState.players.put(id, player);
                        server.sendToAllTCP(player.data);
                    }
                }
            }
        
            for (GamePlayer player : currentGameState.players) {
                player.input = inputDataQueue.getNextInput(player.id, processTime);
            }
            
            currentGameState = new GameState(currentGameState);
            currentGameState.update(16);
            
            server.sendToAllUDP(currentGameState);
    
            timeDisconnected[0] += 16;
            if (timeDisconnected[0] > 15000) {
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