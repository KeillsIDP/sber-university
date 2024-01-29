## Домашнее задание 25.01.24
Дан файл содержащий несколько случайных натуральных чисел от 1 до 50. Необходимо написать многопоточное приложение,  которое параллельно рассчитает и выведет в консоль факториал для каждого числа из файла.
## Реализация
Реализуем функциональный интерфейс Runnable.

```java
public class MultithreadingFactorial implements Runnable {

    private long result;
    private final int number;

    public MultithreadingFactorial(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        result = 1;
        for(int i = 1;i<=number;i++)
            result*=i;
    }
    public long getResult() {
        return result;
    }
}
```
Это требуется для того, чтобы использовать данный класс в Thread. Сам класс в методе run() рассчитывает факториал числа, которое передается в конструкторе.

В классе тестов реализованы два метода для записи и чтения из файла.
```java
void generateNumbers(){
        try(FileOutputStream fos = new FileOutputStream("numbers.txt")){
            for(int i = 1;i<=50;i++)
                fos.write(i);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    List<Integer> readNumbers(){
        List<Integer> list = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream("numbers.txt")){
            for(int i = 1;i<=50;i++)
                list.add(fis.read());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
```