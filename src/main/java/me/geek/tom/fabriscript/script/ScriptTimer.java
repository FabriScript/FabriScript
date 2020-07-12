package me.geek.tom.fabriscript.script;

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
