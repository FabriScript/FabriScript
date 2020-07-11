package me.geek.tom.fabriscript.script.impl;

import me.geek.tom.fabriscript.script.api.BlockLocation;
import me.geek.tom.fabriscript.script.api.IPlayer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class PlayerImpl implements IPlayer {

    private final ServerPlayerEntity player;

    public PlayerImpl(ServerPlayerEntity player) {
        this.player = player;
    }

    @Override
    public void sendMessage(String message) {
        this.player.sendMessage(new LiteralText(message), false);
    }

    @Override
    public void relativeTeleport(double x, double y, double z) {
        this.player.teleport(this.player.getX() + x, this.player.getY() + y, this.player.getZ() + z);
    }

    @Override
    public void teleport(int x, int y, int z) {
        this.player.teleport(x, y, z);
    }

    @Override
    public void teleport(BlockLocation target) {
        this.teleport(target.getX(), target.getY(), target.getZ());
    }

    @Override
    public void applyMotion(int xVelocity, int yVelocity, int zVelocity) {
        this.player.setVelocity(this.player.getVelocity().add(xVelocity, yVelocity, zVelocity));
    }

    @Override
    public BlockLocation getPosition() {
        return new BlockLocationImpl(this.player.getBlockPos());
    }

}
