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

package me.geek.tom.fabriscript.script.api;

public interface IPlayer {

    /**
     * Sends a message to the player that triggered the command.
     * This can contain chat formatting codes.
     *
     * @param message The message to send.
     */
    void sendMessage(String message);

    /**
     * Teleport the player to the given relative location.
     *
     * @param x The relative X coordinate.
     * @param y The relative Y coordinate.
     * @param z The relative Z coordinate.
     */
    void relativeTeleport(double x, double y, double z);

    /**
     * Teleports the player to the given absolute location.
     *
     * @param x The relative X coordinate.
     * @param y The relative Y coordinate.
     * @param z The relative Z coordinate.
     */
    void teleport(int x, int y, int z);

    /**
     * Teleport the player to the absolute given location.
     *
     * @param target The location to teleport the player to.
     */
    void teleport(BlockLocation target);

    /**
     * Apply motion to the player that ran the command. This will be added onto any motion the entity already has.
     *
     * @param xVelocity The X velocity to add.
     * @param yVelocity The Y velocity to add.
     * @param zVelocity The Z velocity to add.
     */
    void applyMotion(int xVelocity, int yVelocity, int zVelocity);

    /**
     * Gets the current position of the player.
     *
     * @return The player's block location.
     */
    BlockLocation getPosition();

}
