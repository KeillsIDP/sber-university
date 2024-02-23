package com.mycompany.app.impl;

import com.mycompany.app.interfaces.Context;
import com.mycompany.app.interfaces.ExecutionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutionManagerImpl implements ExecutionManager {
    private final ExecutorService executorService;

    public ExecutionManagerImpl(int maxThreads) {
        // фиксированный тредпул
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public Context execute(Runnable callback, List<Runnable> tasks) {
        ContextImpl context = new ContextImpl(tasks.size(),executorService);
        List<Future<?>> futures = new ArrayList<>();

        // ставим в очередь на выполнение задачи
        // и работу с контекстом
        for (Runnable task : tasks) {
            futures.add(executorService.submit(() -> {
                try {
                    task.run();
                    context.incrementCompletedTaskCount();
                } catch (Exception e) {
                    context.incrementFailedTaskCount();
                }
            }));
        }

        // после создаем задачу проверки
        // для futures, чтобы узнать завершено ли выполнение
        // всех задач и можем ли мы вызвать callback
        executorService.submit(() -> {
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    context.incrementFailedTaskCount();
                }
            }
            callback.run();
            context.setFinished(true);
        });

        // контекст возвращается сразу после добавления
        // всех задач в пул
        return context;
    }
}
