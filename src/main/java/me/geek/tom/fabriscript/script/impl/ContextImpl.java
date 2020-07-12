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
