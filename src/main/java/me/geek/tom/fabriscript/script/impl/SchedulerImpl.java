package me.geek.tom.fabriscript.script.impl;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
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
    public String scheduleTask(Function callback, int ticks) {
        String taskName = NAME_PREFIX + nextId();
        long time = world.getTime() + (long)ticks;
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
