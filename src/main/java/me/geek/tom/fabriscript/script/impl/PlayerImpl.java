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
