package me.keills;

import java.util.ArrayList;
import java.util.List;

public class GC {
    public static void main(String[] args){
        List<Object> objects = new ArrayList<>();

        // Создаем много объектов разных типов
        for (int i = 0; i < 1000000; i++) {
            if (i % 2 == 0) {
                objects.add(new String("Строка " + i));
            } else {
                objects.add(Integer.valueOf(i));
            }
        }

        // Ждем, пока сборщик мусора соберет объекты
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
