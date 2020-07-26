
var colours = [
    "minecraft:black_concrete",
    "minecraft:blue_concrete",
    "minecraft:brown_concrete",
    "minecraft:cyan_concrete",
    "minecraft:gray_concrete",
    "minecraft:green_concrete",
    "minecraft:light_blue_concrete",
    "minecraft:light_gray_concrete",
    "minecraft:lime_concrete",
    "minecraft:magenta_concrete",
    "minecraft:orange_concrete",
    "minecraft:pink_concrete",
    "minecraft:purple_concrete",
    "minecraft:red_concrete",
    "minecraft:white_concrete",
    "minecraft:yellow_concrete"
];

var player = context.getCaller();
var world = context.getWorld();

for (var i = 0; i < 90; i++) {
    var pos = i * 4;
    var s = sin(pos);
    var yPos = Math.floor(s * 12);

    var block = colours[Math.floor(((s+1) / 2) * colours.length)]

    for (var y = -12; y <= 12; y++) {
        if (y === yPos) {
            world.setBlock(i, y, 0, block);
        } else {
            world.setBlock(i, y, 0, "minecraft:air");
        }
    }
}
