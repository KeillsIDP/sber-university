package dz211223;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args){
        String[] words = {"апельсины","пельмени","котлеты","абрикосы","мандарины","вишни","апельсины","котлеты","грейпфруты","мандарины","сосиски","вишни","яблоки"};

        // используя Stream API преобразовываем массив в Set
        // впоследствии у нас исчезнут повторяющиеся элементы
        Set<String> distinct = Arrays.stream(words).collect(Collectors.toSet());
        System.out.println(distinct);

        // групируем по одинаковым элементам, при этом подсчитывая количество повялений в массиве
        Map<String, Long> counted = Arrays.stream(words).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        System.out.println(counted);

        Phonebook book = new Phonebook();
        System.out.println(book);
        book.add("Иванов","888199000");
        book.add("Романов","888299000");
        book.add("Кузнецов","888399000");
        book.add("Попов","888499000");
        System.out.println(book);
        book.add("Иванов","888599000");
        book.add("Романов","888699000");
        System.out.println(book);
        System.out.println(book.get("Романов"));
        System.out.println(book.get("Антонов"));
    }
}
