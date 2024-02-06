package me.keills;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class FixedThreadPool implements ThreadPool{
    private final Thread[] threads;
    private final LinkedBlockingQueue<Runnable> tasks;

    public FixedThreadPool(int threadCount) {
        tasks = new LinkedBlockingQueue<>();

        threads = new Thread[threadCount];
        for(int i = 0; i<threadCount; i++)
            threads[i] = new Thread(()->{
                while (true) {
                    try {
                        Runnable task = tasks.take();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
    }

    @Override
    public void start() {
        for(Thread thread : threads)
            thread.start();
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            tasks.put(runnable);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
