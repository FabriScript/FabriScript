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

public class ScriptTimerGroup {

    private final ScriptTimer globalTimer;

    private final ScriptTimer setupTimer;
    private final ScriptTimer evalTimer;

    public ScriptTimerGroup() {
        globalTimer = new ScriptTimer();
        setupTimer = new ScriptTimer();
        evalTimer = new ScriptTimer();
    }

    public void startSetup() {
        globalTimer.start();
        setupTimer.start();
    }

    public void startEval() {
        setupTimer.stop();
        evalTimer.start();
    }

    public void complete() {
        evalTimer.stop();
        globalTimer.stop();
    }

    public long getTotalTime() {
        return globalTimer.computeTime();
    }

    public long getSetupTime() {
        return setupTimer.computeTime();
    }

    public long getEvalTime() {
        return evalTimer.computeTime();
    }

}
