package me.shreyasr.ancients;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.component.TexTransform;
import me.shreyasr.ancients.game.GamePlayer;
import me.shreyasr.ancients.game.GameState;
import me.shreyasr.ancients.network.CustomPacketListener;
import me.shreyasr.ancients.util.InputDataQueue;
import me.shreyasr.ancients.util.KryoRegistrar;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        Log.set(Log.LEVEL_INFO);

        InputDataQueue inputDataQueue = new InputDataQueue();

        Server server = new Server();
        KryoRegistrar.register(server.getKryo());
        server.start();
        server.bind(54555, 54777);

        server.addListener(new CustomPacketListener()
                .doOnInputData((conn, inputData) -> inputDataQueue.putInputData(conn.getID(), inputData, System.currentTimeMillis()))
                .doOnDisconnect(conn -> { }));
        
        new Thread(() -> {
            GameState currentGameState = new GameState(System.currentTimeMillis() - 16);
            
            while(true) {
                long processTime = System.currentTimeMillis();
                
                // Create new players for new connections
                for (Connection conn : server.getConnections()) {
                    int id = conn.getID();
                    if (conn.isConnected() && id != -1) {
                        GamePlayer player = currentGameState.players.getById(id);
                        if (player == null) {
                            player = new GamePlayer(id, Asset.PLAYER, new Pos(100, 100), new TexTransform(64, 64));
                            currentGameState.players.put(id, player);
                        }
                    }
                }

                for (GamePlayer player : currentGameState.players) {
                    player.input = inputDataQueue.getNextInput(player.id, processTime);
                }
                
                currentGameState = new GameState(currentGameState);
                currentGameState.update(16);
                
                server.sendToAllUDP(currentGameState);
                
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "GameUpdateThread").start();
    }
}