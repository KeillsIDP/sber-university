package me.keills;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixedThreadPoolTest {

    FixedThreadPool pool;

    @BeforeEach
    void init(){
        pool = new FixedThreadPool(10);
    }

    @Test
    void startAfterLoad(){
        for(int i = 0 ; i<20; i++){
            final int num = i;
            pool.execute(() -> System.out.println(num));
        }
        pool.start();
    }

    @Test
    void startBeforeLoad(){
        pool.start();
        for(int i = 0 ; i<20; i++){
            final int num = i;
            pool.execute(() -> {System.out.println(num);
                try {
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}