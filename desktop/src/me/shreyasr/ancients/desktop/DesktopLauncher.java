package me.shreyasr.ancients.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import me.shreyasr.ancients.AncientsGame;
import me.shreyasr.ancients.util.KryoRegistrar;

import java.io.IOException;

public class DesktopLauncher {
    
    public static void main (String[] arg) throws IOException {
        Client client = new Client();
        Log.set(Log.LEVEL_INFO);
        KryoRegistrar.register(client.getKryo());
        client.start();
        client.connect(2000, "127.0.0.1", 54555, 54777);

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new AncientsGame(client), config);
    }
}
