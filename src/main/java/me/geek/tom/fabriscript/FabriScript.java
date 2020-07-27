/*
 * FabriScript
 * Copyright (C) 2020 Tom_The_Geek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
