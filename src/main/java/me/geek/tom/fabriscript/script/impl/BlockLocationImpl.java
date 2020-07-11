package me.geek.tom.fabriscript.script.impl;

import me.geek.tom.fabriscript.script.api.BlockLocation;
import net.minecraft.util.math.BlockPos;

public class BlockLocationImpl extends BlockLocation {
    public BlockLocationImpl(int x, int y, int z) {
        super(x, y, z);
    }

    public BlockLocationImpl(BlockPos pos) {
        super(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public BlockLocation add(int x, int y, int z) {
        return new BlockLocationImpl(this.getX() + x, this.getY() + y, this.getZ() + z);
    }
}
