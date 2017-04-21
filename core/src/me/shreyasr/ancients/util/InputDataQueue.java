package me.shreyasr.ancients.util;

import com.esotericsoftware.minlog.Log;
import me.shreyasr.ancients.network.InputData;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class InputDataQueue {
    
    private Map<Integer, SingleClientInputDataQueue> clientInputDataQueues = new HashMap<>();
    
    public void putInputData(int clientId, InputData data, long receivedTimeMillis) {
        SingleClientInputDataQueue singleClientQueue = clientInputDataQueues.get(clientId);
        if (singleClientQueue == null) {
            singleClientQueue = new SingleClientInputDataQueue(clientId);
            clientInputDataQueues.put(clientId, singleClientQueue);
        }
        
        singleClientQueue.put(data, receivedTimeMillis);
    }
    
    public InputData getNextInput(int clientId, long processTime) {
        SingleClientInputDataQueue singleClientQueue = clientInputDataQueues.get(clientId);
        if (singleClientQueue == null) {
            Log.trace("inputqueue", "No SingleClientQueue for client " + clientId);
            return new InputData(null, false, false, false, false, false, false, false);
        }
        return singleClientQueue.getNextInput(processTime);
    }
}
class SingleClientInputDataQueue {
    
    private final int clientId;
    private TreeMap<Long, InputData> inputs = new TreeMap<>();
    
    SingleClientInputDataQueue(int clientId) {
        this.clientId = clientId;
    }
    
    void put(InputData data, long receivedTimeMillis) {
        inputs.put(receivedTimeMillis, data);
    }
    
    /**
     * Gets the most recent input received before a certain time. If no such
     * input exists, return an empty input.
     */
    InputData getNextInput(long processTime) {
        // greatest key less than or equal to
        // ie, most recent key before processTime
        Map.Entry<Long, InputData> closestPastEntry = inputs.floorEntry(processTime);
        if (closestPastEntry == null) {
            Log.info("inputqueue", "No past input for client " + clientId);
            return new InputData(null, false, false, false, false, false, false, false);
        }
        return closestPastEntry.getValue();
    }
}
