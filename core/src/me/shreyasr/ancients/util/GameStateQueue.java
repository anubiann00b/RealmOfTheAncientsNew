package me.shreyasr.ancients.util;

import me.shreyasr.ancients.game.GameState;

import java.util.TreeSet;

public class GameStateQueue {
    
    TreeSet<GameState> gameStates = new TreeSet<>();
    
    public GameStateQueue() { }
    
    public void put(GameState data) {
        gameStates.add(data);
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
        
        if (nextState == null) {
            return previousState;
        }
        
        return GameState.interpolate(previousState, nextState, time);
    }
}
