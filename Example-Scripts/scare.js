/*
 * This script shows a simple of the API to scare a player by teleporting them up into the air.
 */

// This function is called once the script is loaded, and is given some parameters about how it has been called.
var main = function(world, ctx, args) {
    // Place a block where they are stood.
    world.setBlock(0, 1, 0, "minecraft:slime_block");
    // Teleport them 200 blocks into the air
    ctx.relativeTeleport(0, 200, 0);
    // Send them a message.
    ctx.sendMessage("Think fast!");
}
