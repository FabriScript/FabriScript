package me.geek.tom.fabriscript.ws;

import me.geek.tom.fabriscript.server.FabriScriptWebSocketServer;
import net.minecraft.server.MinecraftServer;

import java.net.InetSocketAddress;

public class FabriScriptServer {

    private static FabriScriptWebSocketServer wsServer;

    public static void onServerStarting(MinecraftServer server) {
        wsServer = new FabriScriptWebSocketServer(new InetSocketAddress("localhost", 80), new FabriScriptImpl(server));
        wsServer.start();
    }

    public static void onServerStopping() {
        if (wsServer != null) {
            try {
                wsServer.stop(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wsServer = null;
        }
    }
}
