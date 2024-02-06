package me.keills;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScalableThreadPoolTest {
    ScalableThreadPool pool;

    @BeforeEach
    void init(){
        pool = new ScalableThreadPool(3,12);
    }

    @Test
    void bigTaskAmount(){
        for(int i = 0 ; i<2000; i++){
            final int num = i;
            pool.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
            pool.start();
        }
    }
}