package me.geek.tom.fabriscript.script.impl;

import me.geek.tom.fabriscript.script.api.IContext;
import me.geek.tom.fabriscript.script.api.IPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class ContextImpl implements IContext {

    private final MinecraftServer server;
    private final IPlayer player;

    public ContextImpl(ServerPlayerEntity player) {
        this.player = new PlayerImpl(player);
        this.server = player.getServer();
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
}
