package dz180124;

import dz180124.service.Service;
import dz180124.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CacheProxyInvocationHandlerTest {

    CacheProxy cacheProxy = new CacheProxy("src/test/java/dz180124/");

    @BeforeEach
    public void beforeEach(){
        clearDirectory();
    }

    @Test
    void invoke_Cache_InMemory_WithIntegerIdentity() {
        Service testService = new TestService();
        Service cachedService = cacheProxy.cache(testService);
        List<Integer> work1 = cachedService.doWork("work1",10000);
        List<Integer> work2 = cachedService.doWork("work2",100);

        assertNotEquals(work1,work2);
        assertNotEquals(work1.size(),work2.size());
    }

    @Test
    void invoke_Cache_InMemory_WithIntegerAndStringIdentity_ContainerSize1000() {
        Service testService = new TestService();
        Service cachedService = cacheProxy.cache(testService);
        List<Integer> work1 = cachedService.doWork("work1","trash",10000);
        List<Integer> work2 = cachedService.doWork("work1","trash2",10000);

        assertNotEquals(work1,work2);
        assertNotEquals(work1.size(),work2.size());
        assertEquals(1000,work2.size());
    }

    @Test
    void invoke_CacheToFile_WithoutFileCreated(){
        Service testService = new TestService();
        Service cachedService = cacheProxy.cache(testService);
        List<Integer> work1 = cachedService.doWorkToFile("work1","trash",10000);
        List<Integer> work2 = cachedService.doWorkToFile("work1","trash2",10000);

        assertNotEquals(work1,work2);
        assertNotEquals(work1.size(),work2.size());
        assertEquals(1000,work2.size());
    }

    @Test
    void invoke_CacheToFile_WithFileCreated(){
        Service testService = new TestService();
        Service cachedService = cacheProxy.cache(testService);
        cachedService.doWorkToFile("prelim","trash",10000);
        List<Integer> work1 = cachedService.doWorkToFile("work1","trash",10000);
        List<Integer> work2 = cachedService.doWorkToFile("work1","trash2",10000);

        assertEquals(work1,work2);
        assertEquals(work1.size(),work2.size());
    }

    @Test
    void invoke_CacheToZipFile_WithoutFilesCreated(){
        Service testService = new TestService();
        Service cachedService = cacheProxy.cache(testService);
        List<Integer> work1 = cachedService.doWorkToZipFile("work1",10000);
        List<Integer> work2 = cachedService.doWorkToZipFile("work2",10000);

        assertNotEquals(work1,work2);
        assertNotEquals(work1.size(),work2.size());
    }

    @Test
    void invoke_CacheToZipFile_WithFilesCreated(){
        Service testService = new TestService();
        Service cachedService = cacheProxy.cache(testService);
        cachedService.doWorkToZipFile("prelim",10000);
        List<Integer> work1 = cachedService.doWorkToZipFile("work1",10000);
        List<Integer> work2 = cachedService.doWorkToZipFile("work2",10000);

        assertEquals(work1,work2);
        assertEquals(work1.size(),work2.size());
    }

    @Test
    void invoke_CacheToFile_WithNotSerializedClass(){
        Service testService = new TestService();
        Service cachedService = cacheProxy.cache(testService);
        cachedService.notSerialized();
    }

    private void clearDirectory(){
        File dir = new File("src/test/java/dz180124/");
        try {
            for(File file: dir.listFiles()){
                int lastDot = file.getName().lastIndexOf('.');
                String fileExtension = file.getName().substring(lastDot+1);
                if(!fileExtension.equals("java"))
                    Files.delete(file.toPath());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}