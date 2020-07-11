package me.geek.tom.fabriscript.script.api;

@SuppressWarnings("unused")
public interface IContext {

    /**
     * Broadcasts a message to all players on the server/world.
     * This can contain chat formatting codes.
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

}
