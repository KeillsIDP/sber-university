package com.mycompany.app.impl;

import com.mycompany.app.interfaces.Context;

import java.util.concurrent.ExecutorService;

public class ContextImpl implements Context {
    private final int totalTasks;
    private final ExecutorService executorService;
    private volatile int completedTaskCount = 0;
    private volatile int failedTaskCount = 0;
    private volatile int interruptedTaskCount = 0;
    private volatile boolean finished = false;

    public ContextImpl(int totalTasks, ExecutorService executorService) {
        this.totalTasks = totalTasks;
        this.executorService = executorService;
    }

    @Override
    public int getCompletedTaskCount() {
        return completedTaskCount;
    }

    @Override
    public int getFailedTaskCount() {
        return failedTaskCount;
    }

    @Override
    public int getInterruptedTaskCount() {
        return interruptedTaskCount;
    }

    @Override
    public void interrupt() {
        executorService.shutdownNow();
        interruptedTaskCount = totalTasks - completedTaskCount - failedTaskCount;
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    public synchronized void incrementCompletedTaskCount() {
        completedTaskCount++;
    }

    public synchronized void incrementFailedTaskCount() {
        failedTaskCount++;
    }

    public synchronized void setFinished(boolean finished) {
        this.finished = finished;
    }
}
