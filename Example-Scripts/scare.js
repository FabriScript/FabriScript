/*
 * This script shows a simple of the API to scare a player by teleporting them up into the air.
 */

/*
 * The context is a global variable that contains info about how the script has been ran
 * This gets the player and world from the context.
 */
var player = context.getCaller();
var world = context.getWorld();

// Place a block where they are stood.
world.setBlock(0, 0, 0, "minecraft:slime_block");

// Spawn a creeper as they reach the ground.
context.getScheduler().scheduleTask(function() {
    // Spawn a creeper
    world.spawnEntity(0, 0, 0, "minecraft:creeper");
}, 60); // Run this 3 seconds later.

// Teleport them 200 blocks into the air
player.relativeTeleport(0, 100, 0);

// Send them a message.
player.sendMessage("Think fast!");
