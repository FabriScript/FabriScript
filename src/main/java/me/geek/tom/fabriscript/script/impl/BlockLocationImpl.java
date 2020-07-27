/*
 * FabriScript
 * Copyright (C) 2020 Tom_The_Geek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
