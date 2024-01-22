package dz180124.service;

import dz180124.annotation.Cache;
import dz180124.annotation.CacheType;

import java.util.List;

public interface Service {
    @Cache(cacheType = CacheType.IN_MEMORY,identityBy = {Integer.class})
    List<Integer> doWork(String name, int start);

    @Cache(cacheType = CacheType.IN_MEMORY,identityBy = {String.class,Integer.class}, containerSize = 1000)
    List<Integer> doWork(String name, String smthg,int start);

    @Cache(cacheType = CacheType.FILE, fileNamePrefix = "data", containerSize = 1000)
    List<Integer> doWorkToFile(String name, int start);

    @Cache(cacheType = CacheType.FILE, fileNamePrefix = "dataTwo", containerSize = 1000)
    List<Integer> doWorkToFile(String name, String smthg,int start);

    @Cache(cacheType = CacheType.FILE, fileNamePrefix = "zipped", containerSize = 1000, zip = true)
    List<Integer> doWorkToZipFile(String name, int start);

    @Cache(cacheType = CacheType.FILE, fileNamePrefix = "non")
    Service notSerialized();

}
