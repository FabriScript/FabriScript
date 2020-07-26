
var player = context.getCaller();
var world = context.getWorld();

for (var x = 0; x < 20; x++) {
    for (var z = 0; z < 20; z++) {
        var xPos = x + 1;
        var zPos = z + 1;
        var noise = (perlin2d(x/16, z/16)*10);
        world.setBlock(xPos, noise, zPos, "minecraft:stone");
    }
}

player.sendMessage("Hello!");
