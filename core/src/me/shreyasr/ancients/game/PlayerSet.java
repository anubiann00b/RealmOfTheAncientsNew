package me.shreyasr.ancients.game;

import lombok.ToString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@ToString(includeFieldNames = false)
public class PlayerSet implements Iterable<GamePlayer> {
    
    private final Map<Integer, GamePlayer> players;
    
    public PlayerSet() {
        this(new HashMap<>());
    }
    
    public PlayerSet(Map<Integer, GamePlayer> players) {
        this.players = players;
    }
    
    public PlayerSet(PlayerSet other) {
        this.players = new HashMap<>();
        
        for (Map.Entry<Integer, GamePlayer> otherEntry : other.players.entrySet()) {
            this.players.put(otherEntry.getKey(), new GamePlayer(otherEntry.getValue()));
        }
    }
    
    public Optional<GamePlayer> getByIdOpt(int id) {
        return Optional.ofNullable(getById(id));
    }
    
    public GamePlayer getById(int id) {
        return players.get(id);
    }
    
    public void put(int id, GamePlayer entity) {
        players.put(id, entity);
    }
    
    public void remove(int id) {
        players.remove(id);
    }
    
    public Iterator<GamePlayer> iterator() {
        return players.values().iterator();
    }
}

