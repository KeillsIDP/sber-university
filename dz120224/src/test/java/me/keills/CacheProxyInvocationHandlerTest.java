package me.keills;

import me.keills.service.Service;
import me.keills.service.TestService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class CacheProxyInvocationHandlerTest {
    CacheProxy cacheProxy = new CacheProxy("src/test/java/dz180124/");

    @Test
    void invoke_Cache_10Threads_CheckWithNonThread_ForSameResult() {
        Service testService = new TestService();
        Service cachedService = cacheProxy.cache(testService);

        ExecutorService service = Executors.newFixedThreadPool(100);
        Future<List<Integer>> result = null;
        List<Integer> work1 = null;

        for(int i = 0;i<100;i++)
            result = service.submit(()->cachedService.doWork("workTHREAD",10000));

        try {
            Thread.sleep(1000);
            work1 = result.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        List<Integer> work2 = cachedService.doWork("work2",10000);

        assertEquals(work1,work2);
        assertEquals(work1.size(),work2.size());
    }

    @Test
    void invoke_Cache_100Threads_NonThreadFirst() {
        Service testService = new TestService();
        Service cachedService = cacheProxy.cache(testService);

        List<Integer> work1 = cachedService.doWork("work1",10000);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ExecutorService service = Executors.newFixedThreadPool(100);
        Future<List<Integer>> result = null;
        List<Integer> work2 = null;

        for(int i = 0;i<100;i++)
            result = service.submit(()->cachedService.doWork("workTHREAD",10000));

        try {
            Thread.sleep(1000);
            work2 = result.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        assertEquals(work1,work2);
        assertEquals(work1.size(),work2.size());
    }
}