package dz080124.utils;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReflectionUtils {
    public static <T> void printMethods(T obj){
        Method[] publicMethods = obj.getClass().getMethods();
        System.out.println("Public методы класса и super классов\n");
        for (Method method: publicMethods) {
            System.out.println(method);
        }

        Method[] classMethods = obj.getClass().getDeclaredMethods();
        System.out.println("\nМетоды класса\n");
        for (Method method: classMethods) {
            System.out.println(method);
        }
    }

    public static <T> void printGetters(T obj){
        Method[] publicMethods = obj.getClass().getMethods();
        System.out.println("\nPublic getters класса и super классов\n");
        for (Method method: publicMethods) {
            if(method.getParameterCount()!=0)
                continue;
            // "обрезаем" имя метода до 3 символов и проверяем если это get
            // для boolean - is
            // оставляем первые 3 символа
            // проверяем, что это getter
            if(getFirstLetters(3,method.getName().chars()).equals("get")
                    // все точно также,но теперь для 2 символов
                    || getFirstLetters(2,method.getName().chars()).equals("is"))
                System.out.println(method);
        }

        Method[] classMethods = obj.getClass().getDeclaredMethods();
        System.out.println("\nGetters класса\n");
        for (Method method: classMethods) {
            if(method.getParameterCount()!=0)
                continue;
            if(getFirstLetters(3,method.getName().chars()).equals("get")
                    // все точно также,но теперь для 2 символов
                    || getFirstLetters(2,method.getName().chars()).equals("is"))
                System.out.println(method);
        }
    }

    public static String getFirstLetters(int size, IntStream intStream){
        return String.join("",intStream
                .limit(size)
                // преобразуем IntStream в Stream<Integer>
                .boxed()
                // преобразуем из Integer в строку
                .map(c->""+(char)c.intValue())
                // собираем в лист, который по итогу заджойнится в строку
                .collect(Collectors.toList()));
    }

    public static String deleteFirstLetters(int size, IntStream intStream){
        return String.join("",intStream
                .skip(size)
                // преобразуем IntStream в Stream<Integer>
                .boxed()
                // преобразуем из Integer в строку
                .map(c->""+(char)c.intValue())
                // собираем в лист, который по итогу заджойнится в строку
                .collect(Collectors.toList()));
    }
}
