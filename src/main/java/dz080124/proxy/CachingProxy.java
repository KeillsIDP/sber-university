package dz080124.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class CachingProxy implements InvocationHandler {
    private Object target;
    private Map<String,Object> cache;

    private CachingProxy(Object target) {
        this.target = target;
        this.cache = new HashMap<>();
    }

    public static Object createProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new CachingProxy(target)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        String methodName = method.getName();

        // Проверяем, есть ли результат в кэше
        if (cache.containsKey(methodName)) {
            System.out.println("Закешированный результат для метода " + methodName);
            return cache.get(methodName);
        }

        // Вызываем метод на объекте
        Object result = method.invoke(target, args);
        cache.put(methodName, result);

        return result;
    }
}
