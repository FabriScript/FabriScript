/**
 * Represents the location of a block in the world.
 */
declare class BlockLocation {
    private constructor();

    public getX(): number;
    public getY(): number;
    public getZ(): number;
}

/**
 * Callback executed for tasks scheduled with {@link Scheduler}.
 */
type TaskCallback = () => void;

/**
 * Represents a player known to the game.
 */
declare class Player {
    private constructor();

    /**
     * Sends a message to the player that triggered the command.
     * This can contain chat formatting codes.
     *
     * @param message The message to send.
     */
    public sendMessage(message: string): void;

    /**
     * Teleport the player to the given relative location.
     *
     * @param x The relative X coordinate.
     * @param y The relative Y coordinate.
     * @param z The relative Z coordinate.
     */
    public relativeTeleport(x: number, y: number, z: number): void;

    /**
     * Teleports the player to the given absolute location.
     *
     * @param x The relative X coordinate.
     * @param y The relative Y coordinate.
     * @param z The relative Z coordinate.
     */
    public teleport(x: number, y: number, z: number): void;

    /**
     * Teleport the player to the absolute given location.
     *
     * @param target The location to teleport the player to.
     */
    public teleport(target: BlockLocation): void;

    /**
     * Apply motion to the player that ran the command. This will be added onto any motion the entity already has.
     *
     * @param xVelocity The X velocity to add.
     * @param yVelocity The Y velocity to add.
     * @param zVelocity The Z velocity to add.
     */
    public applyMotion(xVelocity: number, yVelocity: number, zVelocity: number): void;

    /**
     * Gets the current position of the player.
     *
     * @return The player's block location.
     */
    public getPosition(): BlockLocation;
}

/**
 * Allows scripts to request that functions are ran at a later time.
 */
declare class Scheduler {
    private constructor();

    /**
     * Schedules a task to be run in a certain number of ticks.
     *
     * @param callback The method to call when the timeout is complete.
     * @param delay How many ticks to wait before the task is ran.
     * @return A unique ID for this task, potentially used to cancel if wanted.
     */
    public scheduleTask(callback: TaskCallback, delay: number): string;
}

declare class World {
    private constructor();

    /**
     * Sets a block relative to the player.
     * If the block ID is invalid, the block will be set to air instead/
     *
     * @param x The relative X coordinate
     * @param y The relative Y coordinate
     * @param z The relative Z coordinate
     * @param block The block to set, in the form <code>namespace:id</code>. Eg. <code>minecraft:dirt</code>
     */
    public setBlock(x: number, y: number, z: number, block: string): void;

    /**
     * Sets a block relative to a player, but suppresses any block updates and forces the state.
     * If the block ID is invalid, the block will be set to air instead.
     *
     * @param x The relative X coordinate
     * @param y The relative Y coordinate
     * @param z The relative Z coordinate
     * @param block The block to set, in the form <code>namespace:id</code>. Eg. <code>minecraft:dirt</code>
     */
    public setBlockNoUpdates(x: number, y: number, z: number, block: string): void;

    /**
     * Sets the block to the given state, this will be parsed like block arguments in commands.
     *
     * @param x The relative X coordinate
     * @param y The relative Y coordinate
     * @param z The relative Z coordinate
     * @param blockState The blockstate to set, in the form used by commands. Eg. <code>minecraft:oak_log[axis=y]</code>
     */
    public setBlockState(x: number, y: number, z: number, blockState: string): void;

    /**
     * Spawns a new entity at the given relative location.
     *
     * @param relX The relative X coordinate
     * @param relY The relative Y coordinate
     * @param relZ The relative Z coordinate
     * @param entity The string name of the entity to spawn, in the form <code>namespace:id</code>. Eg. <code>minecraft:creeper</code>
     */
    public spawnEntity(relX: number, relY: number, relZ: number, entity: string);

    /**
     * Spawns a new entity at the given relative location.
     *
     * @param relX The relative X coordinate
     * @param relY The relative Y coordinate
     * @param relZ The relative Z coordinate
     * @param entity The string name of the entity to spawn, in the form <code>namespace:id</code>. Eg. <code>minecraft:creeper</code>
     * @param naturalSpawn If true, the entity will be spawned as if from natural spawning reasons, otherwise, as if through commands.
     */
    public spawnEntity(relX: number, relY: number, relZ: number, entity: string, naturalSpawn: boolean);
}

declare class Context {
    private constructor();

    /**
     * Broadcasts a message to all players on the server/world.<br>
     * This can contain vanilla chat formatting codes.
     *
     * @param message The message to broadcast.
     */
    public broadcastMessage(message: string): void;

    /**
     * Get the player who triggered the script's execution.
     *
     * @return The player responsible.
     */
    public getCaller(): Player;

    /**
     * Get an object that allows interaction with the physical world.
     *
     * @return The world that the player was in.
     */
    public getWorld(): World;

    /**
     * Gets the instance of the scheduler.
     *
     * @return The current scheduler.
     */
    public getScheduler(): Scheduler;
}

declare const context: Context;

/**
 * The trigonomic function 'sine'
 *
 * @param angle The angle in degrees.
 */
declare function sin(angle: number): number;

/**
 * The trigonomic function 'cosine'
 *
 * @param angle The angle in degrees.
 */
declare function cos(angle: number): number;

/**
 * The trigonomic function 'tan'
 *
 * @param angle The angle in degrees.
 */
declare function tan(angle: number): number;

declare function perlin2d(x: number, y: number);
declare function perlin2d_seeded(x: number, y: number, seed: number);

declare function perlin3d(x: number, y: number, z: number);
declare function perlin3d_seeded(x: number, y: number, z: number, seed: number);
