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

package me.geek.tom.fabriscript.script.timer;

public class ScriptTimer {

    private long startTimeMillis;
    private long endTimeMillis;
    private boolean isRunning;

    public ScriptTimer() {
        this.reset();
    }

    public void start() {
        if (this.isRunning) return; // Dont start if we are already running.

        this.startTimeMillis = System.currentTimeMillis();
        this.isRunning = true;
    }

    public long stop() {
        if (!isRunning) return -1L; // Return invalid if we aren't running.

        this.endTimeMillis = System.currentTimeMillis();
        this.isRunning = false;
        return this.computeTime();
    }

    public long computeTime() {
        return this.endTimeMillis - this.startTimeMillis;
    }

    public void reset() {
        this.startTimeMillis = -1L;
        this.endTimeMillis = -1L;
        this.isRunning = false;
    }
}
