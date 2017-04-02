package me.shreyasr.ancients.game;

public class GameState implements Comparable<GameState> {
    
    public static GameState makeEmptyGameStateWithTime(long time) {
        return new GameState(time);
    }
    
    private long time;
    public PlayerSet players;
    
    protected GameState() { }
    
    public GameState(long time) {
        this(time, new PlayerSet());
    }
    
    public GameState(long time, PlayerSet players) {
        this.time = time;
        this.players = players;
    }
    
    public GameState(GameState other) {
        this(other.time, new PlayerSet(other.players));
    }
    
    /**
     * Returns a GameState representing the state after delta millis. Only called on server.
     */
    public void update(long delta) {
        time += delta;
        for (GamePlayer player : players) {
            player.update();
        }
    }
    
    private void interpolateTo(GameState next, long timeToInterpolateTo) {
        float percentageToNext = (float)(timeToInterpolateTo - this.time) / (next.time - this.time);
        time = timeToInterpolateTo;
        
        for (GamePlayer player : players) {
            GamePlayer nextPlayer = next.players.getById(player.id);
            player.interpolateTo(nextPlayer, percentageToNext);
        }
    }
    
    public static GameState interpolate(GameState previous, GameState next, long time) {
        GameState interpolatedState = new GameState(previous);
        
        interpolatedState.interpolateTo(next, time);
        
        return interpolatedState;
    }
    
    @Override
    public int compareTo(GameState o) {
        return Long.compare(time, o.time);
    }
    
    @Override
    public String toString() {
        return "GameState{" +
                "time=" + time +
                ", players=" + players +
                '}';
    }
}
