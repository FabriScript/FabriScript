package me.geek.tom.fabriscript.script;

public class ScriptTimer {

    private long startTimeMicros;
    private long endTimeMicros;
    private boolean isRunning;

    public ScriptTimer() {
        this.reset();
    }

    public void start() {
        if (this.isRunning) return; // Dont start if we are already running.

        this.startTimeMicros = System.nanoTime() / 1000L;
        this.isRunning = true;
    }

    public long stop() {
        if (!isRunning) return -1L; // Return invalid if we aren't running.

        this.endTimeMicros = System.nanoTime() / 1000L;
        this.isRunning = false;
        return this.computeTime();
    }

    public long computeTime() {
        return this.endTimeMicros - this.startTimeMicros;
    }

    public void reset() {
        this.startTimeMicros = -1L;
        this.endTimeMicros = -1L;
        this.isRunning = false;
    }
}
