package me.shreyasr.ancients.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntitySet implements Iterable<GamePlayer> {
    
    private final Map<Integer, GamePlayer> entities;
    
    EntitySet() {
        this.entities = new HashMap<>();
    }
    
    public GamePlayer getById(int id) {
        return entities.get(id);
    }
    
    public void put(int id, GamePlayer entity) {
        entities.put(id, entity);
    }
    
    public Iterator<GamePlayer> iterator() {
        return entities.values().iterator();
    }
    
    @Override
    public String toString() {
        return "EntitySet{" +
                "entities=" + entities +
                '}';
    }
}

