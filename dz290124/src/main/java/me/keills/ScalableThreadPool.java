package me.keills;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ScalableThreadPool implements ThreadPool{
    private final int minThreads;
    private final int maxThreads;
    private final List<Thread> threads;
    private final LinkedBlockingQueue<Runnable> tasks;

    public ScalableThreadPool(int minThreads, int maxThreads) {
        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        this.tasks = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<>();
        for (int i = 0; i < minThreads; i++)
            addThread();
    }

    @Override
    public void start() {
        // потоки запускаются в конструкторе и при добавлении
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            tasks.put(runnable);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        synchronized (threads) {
            if (!tasks.isEmpty() && threads.size() < maxThreads)
                addThread();
        }
    }

    /**
     * Добавляет поток в pool, и запускает его
     */
    private void addThread() {
        Thread thread = new Thread(()->{
            while (true) {
                try {
                    Runnable task = tasks.take();
                    task.run();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                synchronized (threads) {
                    if (tasks.isEmpty() && threads.size() > minThreads) {
                        threads.remove(Thread.currentThread());
                        return;
                    }
                }
                System.out.println("Current thread amount: " + threads.size());
            }
        });
        threads.add(thread);
        thread.start();
    }
}
