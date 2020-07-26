package me.geek.tom.fabriscript;

import me.geek.tom.fabriscript.command.ModCommands;
import me.geek.tom.fabriscript.ws.FabriScriptServer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import java.io.File;

public class FabriScript implements ModInitializer {
    @Override
    public void onInitialize() {
        ModCommands.init();
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            File scriptDir = new File(server.getRunDirectory(), "fabriscript");
            if (!scriptDir.exists())
                scriptDir.mkdirs();

            FabriScriptServer.onServerStarting(server);
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(server ->
                FabriScriptServer.onServerStopping());
    }
}
