package dz080124.annotation;

import dz080124.proxy.CachingProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class PerformanceProxy implements InvocationHandler {
    private Object target;

    private PerformanceProxy(Object target) {
        this.target = target;
    }

    public static Object createProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new PerformanceProxy(target)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if(method.isAnnotationPresent(Metric.class)){
            long start = System.nanoTime();
            Object result = method.invoke(target,args);
            System.out.println("Затраченное время - " + (System.nanoTime()-start) + " нс");
            return result;
        }
        return null;
    }
}
