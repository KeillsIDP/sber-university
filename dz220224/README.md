# Задание 1
Сделать цикл на 100000 итераций, в цикле в предварительно созданную Map<Integer, String>
сложить ключ - индекс, значение - "value" + индекс Запустить с опцией -XX:+PrintCompilation,
проанализировать информацию в консоли Запустить с опцией -XX:+PrintCompilation
-XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining , проанализировать информацию в консоли.

<b>Код программы</b>
```java
public class JITCompile {
    public static void  main(String[] args){
        Map<Integer,String> map = new HashMap<>();
        for (int i = 0; i<100000; i++)
            map.put(i,"value"+i);
    }
}
```
Компиляция с параметром -XX:+PrintCompilation вывела в консоль используемы программой методы,
пакеты и классы, также кол-во байт, занимаемое инструкцией.</br> Вот пример одной строки из консоли:
```
 177 4 3 jdk.internal.util.ArraysSupport::signedHashCode (37 bytes) 
```
Компиляция с параметром -XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining.
Полученный вывод в консоль немного отличается от предыдущего, так как мы пользуемся инлайнингом.
Например, метод ```java.lang.Math::floorMod``` был инлайнирован в инструкцию номер 29.
Это означает, что код метода floorMod был непосредственно вставлен в код вызывающего метода, что может улучшить производительность за счет устранения вызова метода.

# Задание 2
Не вышло использовать Visual GC, он денег просит. Попытался использовать VisualVM, но при изменении
используемого GC, там ничего не менялось. 