package me.keills;

import java.lang.reflect.Proxy;

public class CacheProxy {
    private final String rootDerictory;

    public CacheProxy(String rootDerictory){
        this.rootDerictory = rootDerictory;
    }

    public <T> T cache(T toCache){
        return (T) Proxy.newProxyInstance(
                toCache.getClass().getClassLoader(),
                toCache.getClass().getInterfaces(),
                new CacheProxyInvocationHandler(toCache,rootDerictory)
        );
    }
}
