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

import me.geek.tom.fabriscript.script.api.IScheduler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.timer.Timer;
import net.minecraft.world.timer.TimerCallback;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class SchedulerImpl implements IScheduler {

    private static final String NAME_PREFIX = "script_scheduled_task_";
    private static int currentId = 0;

    private static int nextId() {
        return currentId++;
    }

    private final Timer<MinecraftServer> internalTimer;
    private final World world;

    @SuppressWarnings("ConstantConditions")
    public SchedulerImpl(World world) {
        this.internalTimer = world.getServer().getSaveProperties().getMainWorldProperties().getScheduledEvents();
        this.world = world;
    }

    @Override
    public String scheduleTask(Function callback, int delay) {
        String taskName = NAME_PREFIX + nextId();
        long time = world.getTime() + (long) delay;
        internalTimer.setEvent(taskName, time, new ScheduledScriptTask(callback));
        return taskName;
    }

    private static class ScheduledScriptTask implements TimerCallback<MinecraftServer> {
        private final Function callback;
        private Context ctx;

        private ScheduledScriptTask(Function callback) {
            ctx = Context.enter();
            this.callback = callback;
        }

        @Override
        public void call(MinecraftServer server, Timer<MinecraftServer> events, long time) {
            Scriptable scope = callback.getParentScope();
            callback.call(ctx, scope, scope, new Object[0]);
            Context.exit();
        }
    }
}
