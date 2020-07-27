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

/**
 * Represents the context in which a script is ran,
 * and contains access to things like the player and world.
 */
@SuppressWarnings("unused")
public interface IContext {

    /**
     * Broadcasts a message to all players on the server/world.<br>
     * This can contain vanilla chat formatting codes.
     *
     * @param message The message to broadcast.
     */
    void broadcastMessage(String message);

    /**
     * Get the player who triggered the script's execution.
     *
     * @return The player responsible.
     */
    IPlayer getCaller();

    /**
     * Get an object that allows interaction with the physical world.
     *
     * @return The world that the player was in.
     */
    IWorld getWorld();

    /**
     * Gets the instance of the scheduler.
     *
     * @return The current scheduler.
     */
    IScheduler getScheduler();

}
