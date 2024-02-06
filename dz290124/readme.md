## Домашнее задание 29.01.24
Реализовать ThreadPool:
```java
public interface ThreadPool { 
void start(); // Запускает потоки. Потоки бездействуют,
// До тех пор, пока не появится новое задание в очереди (см. execute) 
void execute(Runnable runnable); // Складывает это задание в очередь. Освободившийся 
// Поток должен выполнить это задание. Каждое задание должны быть выполнено ровно 1 раз 
} 
```
Сделать 2 реализации ThreadPool: 
1) FixedThreadPool - Количество потоков задается в конструкторе и не меняется. 
2) ScalableThreadPool в конструкторе задается минимальное и максимальное(int min, int max) 
число потоков, количество запущенных потоков может быть увеличено от минимального к максимальному,
если при добавлении нового задания в очередь нет свободного потока для исполнения этого задания. 
При отсутствии задания в очереди, количество потоков опять должно быть уменьшено до значения min
## Реализация
<b>FixedThreadPool</b> - для взаимодействия с потоками будем использовать массив,
длина которого задается в конструкторе. Все задачи будут храниться в LinkedBlockedQueue, так как она 
потока безопасна.\
Каждый поток при своем запуске находится в цикле, пока его не прервут. 
Каждую итерацию, поток пытается взять новую задачу из очереди и выполнить её.
```java
public class FixedThreadPool implements ThreadPool{
    private final Thread[] threads;
    private final LinkedBlockingQueue<Runnable> tasks;

    public FixedThreadPool(int threadCount) {
        tasks = new LinkedBlockingQueue<>();

        threads = new Thread[threadCount];
        for(int i = 0; i<threadCount; i++)
            threads[i] = new Thread(()->{
                while (true) {
                    try {
                        Runnable task = tasks.take();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
    }

    @Override
    public void start() {
        for(Thread thread : threads)
            thread.start();
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            tasks.put(runnable);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
```
<b>ScalableThreadPool</b> - теперь вместо массива используется ArrayList, размер корректируется при добавлении новой задачи,
а также каждым потоком самостоятельно. Если потоков больше чем нужно, и при этом нет каких-либо задач в очереди
потоки удаляются из листа и прерывают свою работу. Потоки запускаются при добавлении.
```java
public class ScalableThreadPool implements ThreadPool{
    private final int minThreads;
    private final int maxThreads;
    private final List<Thread> threads;
    private final LinkedBlockingQueue<Runnable> tasks;

    public ScalableThreadPool(int minThreads, int maxThreads) {
        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        this.tasks = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<>();
        for (int i = 0; i < minThreads; i++)
            addThread();
    }

    @Override
    public void start() {
        // потоки запускаются в конструкторе и при добавлении
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            tasks.put(runnable);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        synchronized (threads) {
            if (!tasks.isEmpty() && threads.size() < maxThreads)
                addThread();
        }
    }

    /**
     * Добавляет поток в pool, и запускает его
     */
    private void addThread() {
        Thread thread = new Thread(()->{
            while (true) {
                try {
                    Runnable task = tasks.take();
                    task.run();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                synchronized (threads) {
                    if (tasks.isEmpty() && threads.size() > minThreads) {
                        threads.remove(Thread.currentThread());
                        return;
                    }
                }
                System.out.println("Current thread amount: " + threads.size());
            }
        });
        threads.add(thread);
        thread.start();
    }
}
```

## Ответы на вопросы
1. Как получить ссылку на текущий поток - Thread.currentThread()
2. Зачем нужно ключевое слово synchronized ? На что его можно вещать(поле, метод, класс, конструктор..) ? -\
Только один поток может войти в `synchronized` код, 
в то время как все остальные потоки, которые пытаются сделать
то же самое, будут заблокированы до тех пор, пока первый поток не выйдет из 
`synchronized` кода.\
`synchronized` используется на методах, а также в блоках кода для синхронизации переменных.
3. Захват какого монитора происходит при входе в synchronized метод/статик метод/блок ?
   - **Synchronized метод**: Когда метод объявлен как `synchronized`, монитор, 
     который захватывается, это монитор объекта, на котором вызывается метод. 
     В случае с нестатическим методом, это будет монитор текущего объекта (`this`).
   - **Synchronized статический метод**: Когда статический метод объявлен как 
     `synchronized`, монитор, который захватывается, это монитор объекта класса 
     (`Class` object) этого метода.
   - **Synchronized блок**: Когда используется `synchronized` блок, монитор, который
     захватывается, это монитор объекта, указанного в скобках после ключевого слова `synchronized`.
4. Зачем нужно ключевое слово volatile ? На что его можно вещать(поле, метод, класс, конструктор..) ? -\
`volatile` даёт потокам записывать значение и читать его из основной памяти программы, а не из памяти потока.
5. Что делает метод Object.wait, Object.notify, Object.notifyAll -
    - **Object.wait** - заставляет текущий поток перейти в ожидание, 
        до того как его не пробудят, также освобождает блокировку на объекте
    - **Object.notify** - пробуждает один из потоков, находящихся в состоянии ожидания 
        на данном потоке, какой именно поток пробудится, будет решать планировщик потоков
    - **Object.notifyAll** - пробуждает все потоки на объекте
   Эти методы должны вызываться из `synchronized` контекста.
6. Что за исключение IllegalMonitorStateException ? - это unchecked exception,
которое обычно выбрасывается, когда поток пытается ожидать, 
уведомить или войти/выйти из монитора объекта, если он не владеет блокировкой на этом объекте.
7. Что делает метод Thread#join ? - этот метод используется, для ожидания окончания 
выполнения выбранного потока
8. Что делает метод Thread#interrupt ? - прерывает выполнение потока
