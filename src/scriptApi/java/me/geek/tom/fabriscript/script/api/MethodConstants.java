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
