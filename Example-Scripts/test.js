/*
 * This is a simple example script!
 *
 * It shows off most of the features of FabriScript.
 */

/*
 * List of axis used for random selection.
 */
var AXIS = [
    "x", "y", "z"
];

/*
 * These will be useful in any script that interacts with the player/world.
 *
 * The context object contains things about the environment the script was ran in, such
 * as the player and world.
 */
var player = context.getCaller();
var world = context.getWorld();

/*
 * You can send messages to the player, or broadcast to the entire server using #broadcastMessage
 */
player.sendMessage("Running with args: " + args);

/*
 * These loops show off the abilities of world interaction by placing randomly oriented oak logs around the player
 */
for (var x = -1; x < 2; x++) {
    for (var y = 0; y < 2; y++) {
        for (var z = -1; z < 2; z++) {
            // First we pick a random number between 0 and 2, to use for the axis
            var rand = Math.floor(Math.random()*3);
            // We then select that axis from the array created at the start.
            var axis = AXIS[rand];
            // And finally set the block in the world.
            world.setBlockState(x, y, z, "minecraft:oak_log[axis=" + axis + "]");
        }
    }
}

/*
 * You can teleport the player using #teleport and #relativeTeleport, and here we also throw the player off in a random direction.
 */
player.relativeTeleport(0, 100, 0);

// TODO: Fix the application of motion onto the player.
//context.applyMotion((Math.floor(Math.random()))*3, 1, Math.floor(Math.random())*3);
