package me.shreyasr.ancients.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import me.shreyasr.ancients.AncientsGame;
import me.shreyasr.ancients.util.InterceptingKryoSerialization;
import me.shreyasr.ancients.util.KryoRegistrar;

import java.io.IOException;

public class DesktopLauncher {
    
    public static void main (String[] arg) throws IOException {
        Client client = new Client(8192, 2048, new InterceptingKryoSerialization());
        Log.set(Log.LEVEL_INFO);
        KryoRegistrar.register(client.getKryo());
        client.start();

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new AncientsGame(client), config);
    }
}
