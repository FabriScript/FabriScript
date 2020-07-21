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
