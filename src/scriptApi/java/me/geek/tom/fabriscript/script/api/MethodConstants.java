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

package me.geek.tom.fabriscript.script.api;

import org.lwjgl.stb.STBPerlin;

public class MethodConstants {

    public static double sin(double angle) {
        return Math.sin(Math.toRadians(angle));
    }

    public static double cos(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public static double tan(double angle) {
        return Math.tan(Math.toRadians(angle));
    }

    public static float perlin2d(double x, double y) {
        return STBPerlin.stb_perlin_noise3((float)x, 0, (float)y, 0, 0, 0);
    }

    public static double perlin2d_seeded(double x, double y, int seed) {
        return STBPerlin.stb_perlin_noise3_seed((float)x, 0, (float)y, 0, 0, 0, seed);
    }

    public static double perlin3d(double x, double y, double z) {
        return STBPerlin.stb_perlin_noise3((float)x, (float)y, (float)z, 0, 0, 0);
    }

    public static double perlin3d_seeded(double x, double y, double z, int seed) {
        return STBPerlin.stb_perlin_noise3_seed((float)x, (float)y, (float)z, 0, 0, 0, seed);
    }

}
