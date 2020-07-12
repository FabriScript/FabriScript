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
