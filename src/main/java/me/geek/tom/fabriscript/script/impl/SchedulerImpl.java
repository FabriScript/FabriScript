package me.geek.tom.fabriscript.script.impl;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import me.geek.tom.fabriscript.script.api.IScheduler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.timer.Timer;
import net.minecraft.world.timer.TimerCallback;

public class SchedulerImpl implements IScheduler {

    private static final String NAME_PREFIX = "script_scheduled_task_";
    private static int currentId = 0;

    private static int nextId() {
        return currentId++;
    }

    private final Timer<MinecraftServer> internalTimer;
    private final World world;

    public SchedulerImpl(World world) {
        this.internalTimer = world.getServer().getSaveProperties().getMainWorldProperties().getScheduledEvents();
        this.world = world;
    }

    @Override
    public String scheduleTask(ScriptObjectMirror callback, int ticks) {
        String taskName = NAME_PREFIX + nextId();
        long time = world.getTime() + (long)ticks;
        internalTimer.setEvent(taskName, time, new ScheduledScriptTask(callback));
        return taskName;
    }

    private static class ScheduledScriptTask implements TimerCallback<MinecraftServer> {
        private final ScriptObjectMirror callback;

        private ScheduledScriptTask(ScriptObjectMirror callback) {
            this.callback = callback;
        }

        @Override
        public void call(MinecraftServer server, Timer<MinecraftServer> events, long time) {
            callback.call(null);
        }
    }
}
