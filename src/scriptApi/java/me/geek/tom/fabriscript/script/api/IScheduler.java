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

import org.mozilla.javascript.Function;

/**
 * Allows scripts to request that functions are ran at a later time.
 */
public interface IScheduler {

    /**
     * Schedules a task to be run in a certain number of ticks.
     *
     * @param callback The method to call when the timeout is complete.
     * @param delay How many ticks to wait before the task is ran.
     * @return A unique ID for this task, potentially used to cancel if wanted.
     */
    String scheduleTask(Function callback, int delay);

}
