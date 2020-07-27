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

public interface IWorld {
    /**
     * Sets a block relative to the player.
     * If the block ID is invalid, the block will be set to air instead/
     *
     * @param x The relative X coordinate
     * @param y The relative Y coordinate
     * @param z The relative Z coordinate
     * @param block The block to set, in the form <code>namespace:id</code>. Eg. <code>minecraft:dirt</code>
     */
    void setBlock(int x, int y, int z, String block);

    /**
     * Sets a block relative to a player, but suppresses any block updates and forces the state.
     * If the block ID is invalid, the block will be set to air instead.
     *
     * @param x The relative X coordinate
     * @param y The relative Y coordinate
     * @param z The relative Z coordinate
     * @param block The block to set, in the form <code>namespace:id</code>. Eg. <code>minecraft:dirt</code>
     */
    void setBlockNoUpdates(int x, int y, int z, String block);

    /**
     * Sets the block to the given state, this will be parsed like block arguments in commands.
     *
     * @param x The relative X coordinate
     * @param y The relative Y coordinate
     * @param z The relative Z coordinate
     * @param blockState The blockstate to set, in the form used by commands. Eg. <code>minecraft:oak_log[axis=y]</code>
     */
    void setBlockState(int x, int y, int z, String blockState);

    /**
     * Spawns a new entity at the given relative location.
     *
     * @param relX The relative X coordinate
     * @param relY The relative Y coordinate
     * @param relZ The relative Z coordinate
     * @param entity The string name of the entity to spawn, in the form <code>namespace:id</code>. Eg. <code>minecraft:creeper</code>
     */
    void spawnEntity(int relX, int relY, int relZ, String entity);

    /**
     * Spawns a new entity at the given relative location.
     *
     * @param relX The relative X coordinate
     * @param relY The relative Y coordinate
     * @param relZ The relative Z coordinate
     * @param entity The string name of the entity to spawn, in the form <code>namespace:id</code>. Eg. <code>minecraft:creeper</code>
     * @param naturalSpawn If true, the entity will be spawned as if from natural spawning reasons, otherwise, as if through commands.
     */
    void spawnEntity(int relX, int relY, int relZ, String entity, boolean naturalSpawn);
}
