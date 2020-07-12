/*
 * This script shows a simple of the API to scare a player by teleporting them up into the air.
 */

// This function is called once the script is loaded, and is given some parameters about how it has been called.
var main = function(ctx, args) {
    // Get the player and world from the context.
    var player = ctx.getCaller();
    var world = ctx.getWorld();

    // Place a block where they are stood.
    world.setBlock(0, 0, 0, "minecraft:slime_block");

    // Spawn a creeper as they reach the ground.
    ctx.getScheduler().scheduleTask(function() {
        // Spawn a creeper
        world.spawnEntity(0, 0, 0, "minecraft:creeper");
    }, 60); // Run this 3 seconds later.

    // Teleport them 200 blocks into the air
    player.relativeTeleport(0, 100, 0);

    // Send them a message.
    player.sendMessage("Think fast!");
}
