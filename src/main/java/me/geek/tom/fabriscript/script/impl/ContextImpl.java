package me.geek.tom.fabriscript.script.impl;

import me.geek.tom.fabriscript.script.api.IContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class ContextImpl implements IContext {

    private ServerPlayerEntity player;
    private MinecraftServer server;

    public ContextImpl(ServerPlayerEntity player) {
        this.player = player;
        this.server = this.player.getServer();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(new LiteralText(message), false);
    }

    @Override
    public void broadcastMessage(String message) {
        this.server.getPlayerManager().getPlayerList().forEach(player ->
                player.sendMessage(new LiteralText(message), false));
    }

    @Override
    public void relativeTeleport(double x, double y, double z) {
        player.teleport(player.getX() + x, player.getY() + y, player.getZ() + z);
    }

    @Override
    public void teleport(int x, int y, int z) {
        player.teleport(x, y, z);
    }

    @Override
    public void applyMotion(int xVelocity, int yVelocity, int zVelocity) {
        this.player.setVelocity(this.player.getVelocity().add(xVelocity, yVelocity, zVelocity));
    }
}
