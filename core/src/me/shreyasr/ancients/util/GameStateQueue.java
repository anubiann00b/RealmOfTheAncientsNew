package me.shreyasr.ancients.util;

import me.shreyasr.ancients.game.GamePlayer;
import me.shreyasr.ancients.game.GameState;
import me.shreyasr.ancients.game.PlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class GameStateQueue {
    
    private TreeSet<GameState> gameStates = new TreeSet<>();
    private Map<Integer, PlayerData> pendingPlayerData = new HashMap<>();
    
    public GameStateQueue() { }
    
    public void put(GameState gameState) {
        addPlayerDataToPlayers(gameState);
        gameStates.add(gameState);
    }
    
    public void put(int id, PlayerData playerData) {
        pendingPlayerData.put(id, playerData);
    }
    
    /**
     * Gets the most recent input received before a certain time. If no such
     * input exists, return an empty input.
     */
    public GameState getInterpolatedCurrentState(long time) {
        GameState emptyStateAtTime = GameState.makeEmptyGameStateWithTime(time);
        GameState nextState = gameStates.ceiling(emptyStateAtTime);
        GameState previousState = gameStates.floor(emptyStateAtTime);
        
        
        if (previousState == null) {
            return emptyStateAtTime;
        }
        addPlayerDataToPlayers(previousState);
        
        if (nextState == null) {
            return previousState;
        }
        addPlayerDataToPlayers(nextState);
        
        return GameState.interpolate(previousState, nextState, time);
    }
    
    private void addPlayerDataToPlayers(GameState state) {
        for (GamePlayer player : state.players) {
            if (player.data == null) {
                player.setPlayerData(pendingPlayerData.get(player.id));
            }
        }
    }
}
