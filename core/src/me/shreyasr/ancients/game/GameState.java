package me.shreyasr.ancients.game;

public class GameState implements Comparable<GameState> {
    
    public static GameState makeEmptyGameStateWithTime(long time) {
        return new GameState(time);
    }
    
    private long time;
    public EntitySet players = new EntitySet();
    
    protected GameState() { }
    
    public GameState(long time) {
        this.time = time;
    }
    
    /**
     * Returns a GameState representing the state after delta millis. Only called on server.
     */
    public GameState update(long delta) {
        GameState newState = new GameState(time + delta);
        for (GamePlayer player : players) {
            GamePlayer newPlayer = new GamePlayer(player.id, player.asset,
                    player.x + (player.input.a ? -10 : 0) + (player.input.d ? 10 : 0),
                    player.y + (player.input.s ? -10 : 0) + (player.input.w ? 10 : 0), player.ttc);
            newState.players.put(player.id, newPlayer);
        }
        return newState;
    }
    
    public static GameState interpolate(GameState previous, GameState next, long time) {
        float percentageToNext = (float)(time - previous.time) / (next.time - previous.time);
        GameState interpolatedState = new GameState(time);
        for (GamePlayer prevPlayer : previous.players) {
            GamePlayer nextPlayer = next.players.getById(prevPlayer.id);
            
            GamePlayer newPlayer = new GamePlayer(prevPlayer.id, prevPlayer.asset,
                    prevPlayer.x * (1-percentageToNext) + nextPlayer.x * percentageToNext,
                    prevPlayer.y * (1-percentageToNext) + nextPlayer.y * percentageToNext, prevPlayer.ttc);
            interpolatedState.players.put(prevPlayer.id, newPlayer);
        }
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
