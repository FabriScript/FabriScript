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
