package me.geek.tom.fabriscript.script.api;

@SuppressWarnings("unused")
public interface IContext {

    /**
     * Sends a message to the player that triggered the command.
     * This can contain chat formatting codes.
     *
     * @param message The message to send.
     */
    void sendMessage(String message);

    /**
     * Broadcasts a message to all players on the server/world.
     * This can contain chat formatting codes.
     *
     * @param message The message to broadcast.
     */
    void broadcastMessage(String message);

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
     * Apply motion to the player that ran the command. THis will be added onto any motion the entity already has.
     *
     * @param xVelocity The X velocity to add.
     * @param yVelocity The Y velocity to add.
     * @param zVelocity The Z velocity to add.
     */
    void applyMotion(int xVelocity, int yVelocity, int zVelocity);

}
