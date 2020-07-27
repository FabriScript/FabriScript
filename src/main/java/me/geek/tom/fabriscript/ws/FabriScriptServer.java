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
