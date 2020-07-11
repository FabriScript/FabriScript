package me.geek.tom.fabriscript.script.api;

public interface IWorldInterface {
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
     * If the block ID is invalid, the block will be set to air instead/
     *
     * @param x The relative X coordinate
     * @param y The relative Y coordinate
     * @param z The relative Z coordinate
     * @param block The block to set, in the form <code>namespace:id</code>. Eg. <code>minecraft:dirt</code>
     */
    void setBlockNoUpdates(int x, int y, int z, String block);

    /**
     * Sets the block to the given state, this will be parsed like block arguments in commands,
     *
     * @param x The relative X coordinate
     * @param y The relative Y coordinate
     * @param z The relative Z coordinate
     * @param blockState The blockstate to set, in the form used by commands. Eg. <code>minecraft:oak_log[axis=y]</code>
     */
    void setBlockState(int x, int y, int z, String blockState);
}
