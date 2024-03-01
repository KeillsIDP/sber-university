package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void smallAmountOfThreadsWithSLeep() {
        Task<Long> task = new Task<>(() -> {
            System.out.println("Ended - " +Thread.currentThread().getName());
            Thread.sleep(1000);
            return System.currentTimeMillis()/1000;
        });
        for(int i = 0; i<10; i++){
            Thread name = new Thread(()->task.get());
            System.out.println("Started - " + name.getName());
            name.start();
        }
    }
    @Test
    void smallAmountOfThreads() {
        Task<Long> task = new Task<>(() -> {
            System.out.println("Ended - " +Thread.currentThread().getName());
            return System.currentTimeMillis()/1000;
        });
        for(int i = 0; i<10; i++){
            Thread name = new Thread(()->task.get());
            System.out.println("Started - " + name.getName());
            name.start();
        }
    }

    @Test
    void largeAmountOfThreadsWithSLeep() {
        Task<Long> task = new Task<>(() -> {
            System.out.println("Ended - " +Thread.currentThread().getName());
            Thread.sleep(1000);
            return System.currentTimeMillis()/1000;
        });
        for(int i = 0; i<1000; i++){
            Thread name = new Thread(()->task.get());
            System.out.println("Started - " + name.getName());
            name.start();
        }
    }
    @Test
    void largeAmountOfThreads() {
        Task<Long> task = new Task<>(() -> {
            System.out.println("Ended - " +Thread.currentThread().getName());
            return System.currentTimeMillis()/1000;
        });
        for(int i = 0; i<1000; i++){
            Thread name = new Thread(()->task.get());
            System.out.println("Started - " + name.getName());
            name.start();
        }

    }
}