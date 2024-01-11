package dz080124;

import dz080124.annotation.PerformanceProxy;
import dz080124.proxy.CachingProxy;
import dz080124.proxy.Service;
import dz080124.proxy.ServiceImpl;
import dz080124.utils.BeanUtils;
import dz080124.utils.ReflectionUtils;
import dz080124.utils.StringConstants;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Main {
    public static void main(String args[]){
        Calculator calculator = new CalculatorImpl();
        ReflectionUtils.printMethods(calculator);

        Integer intTest = Integer.valueOf(1);
        ReflectionUtils.printMethods(intTest);

        // вывод геттеров
        ReflectionUtils.printGetters(calculator);

        // проверка строк
        System.out.println("\nНазвание строки / Значение / Равны ли они");
        Arrays.stream(StringConstants.class.getDeclaredFields())
                .forEach(x-> {
                    try {
                        int modifier = x.getModifiers();
                        if(Modifier.isStatic(modifier) && Modifier.isFinal(modifier))
                            // get(null) на поле для статического / для определенного объекта мы должны передать его
                            System.out.println(x.getName() + " / " + x.get(null) + " / " + x.getName().equals(x.get(null)));
                    } catch (IllegalAccessException e) {
                        System.out.println("error");
                    }
                });

        // кеширующий прокси
        Service realService = new ServiceImpl();
        Service proxy = (Service) CachingProxy.createProxy(realService);

        System.out.println();
        // Первый вызов метода, данные будут получены и закэшированы
        System.out.println(proxy.doJob());
        // Второй вызов метода, данные будут взяты из кэша
        System.out.println(proxy.doJob());

        // performance прокси
        System.out.println();
        Calculator perfProx = (Calculator) PerformanceProxy.createProxy(calculator);
        System.out.println(perfProx.calc(20));

        //beanutils
        System.out.println();
        TestTo to = new TestTo();
        TestFrom from = new TestFrom(20,1.5d,3);
        BeanUtils.assign(to,from);
        System.out.println(to);
    }


}
