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

package me.geek.tom.fabriscript.script.impl;

import me.geek.tom.fabriscript.script.api.IContext;
import me.geek.tom.fabriscript.script.api.IPlayer;
import me.geek.tom.fabriscript.script.api.IScheduler;
import me.geek.tom.fabriscript.script.api.IWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;

public class ContextImpl implements IContext {

    private final MinecraftServer server;
    private final IPlayer player;
    private final IWorld world;
    private final IScheduler scheduler;

    public ContextImpl(ServerPlayerEntity player) {
        this.player = new PlayerImpl(player);
        this.server = player.getServer();
        World world = player.getEntityWorld();
        this.world = new WorldImpl(player, world);
        this.scheduler = new SchedulerImpl(world);
    }

    @Override
    public void broadcastMessage(String message) {
        this.server.getPlayerManager().getPlayerList().forEach(player ->
                player.sendMessage(new LiteralText(message), false));
    }

    @Override
    public IPlayer getCaller() {
        return player;
    }

    @Override
    public IWorld getWorld() {
        return world;
    }

    @Override
    public IScheduler getScheduler() {
        return scheduler;
    }
}
