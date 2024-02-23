package com.mycompany.app;

import java.util.concurrent.Callable;

public class Task<T> {
    private Callable<? extends T> callable;
    private T savedResult;
    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    /**
     * Синхронизированный метод, который
     * вызывает метод call, объекта callable.
     * Так как он синхронизирован, то до
     * выполнения в начальном потоке, он не будет
     * работать в других потоках
     * @return закешированный или высчитаный результат
     */
    public synchronized T get() {
        if(savedResult!=null)
            return savedResult;

        try {
            savedResult = callable.call();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при доступе к объекту из потока - " + e.getMessage());
        }

        return savedResult;
    }
}

