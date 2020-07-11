package me.geek.tom.fabriscript.script.api;

public abstract class BlockLocation {

    private final int x;
    private final int y;
    private final int z;

    public BlockLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public abstract BlockLocation add(int x, int y, int z);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
