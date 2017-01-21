package me.shreyasr.ancients.network;

import java.util.LinkedList;
import java.util.Queue;

public class PacketQueue {

    private Queue<InputPacket> packets = new LinkedList<>();

    public void addPacket(InputPacket packet) {
        packets.add(packet);
    }

    public InputPacket popPacket() {
        return packets.poll();
    }
}
