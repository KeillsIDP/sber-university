package com.mycompany.app.impl;

import com.mycompany.app.Task;
import com.mycompany.app.interfaces.Context;
import com.mycompany.app.interfaces.ExecutionManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionManagerImplTest {

    @Test
    void execute() {
        ExecutionManager manager = new ExecutionManagerImpl(10);
        List<Runnable> tasks = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            int num = i;
            tasks.add(() -> System.out.println("Task №"+num));
        }

        Context context = manager.execute(()-> System.out.println("Execution end"),tasks);
        try {
            Thread.sleep(1000);
            assertEquals(context.getCompletedTaskCount(),100);
            assertTrue(context.isFinished());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}