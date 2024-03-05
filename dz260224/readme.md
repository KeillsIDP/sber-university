# Задание
Создать CacheProxy, который будет работать после перезапуска приложения.
В своей реализации я использовал PostgreSQL. Пример работы находится в тестах.</br>

У аннотации нет параметров, хотя можно было бы доделать прокси, который мы делали ранее.
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Cacheable {
}
```

Для реализации прокси я использовал рефлексию - создал интерфейс 
калькулятора и его имплементацию(последовательность считается не рекурсивно),
после создал новый инстанс прокси:

```java
Calculator calc = new CalculatorImpl();
        calcProxy = (Calculator) Proxy.newProxyInstance(
                calc.getClass().getClassLoader(),
                calc.getClass().getInterfaces(),
                new DataBaseProxyInvocationHandler(calc,db_url, db_username, db_password)
        );
```

Класс ```DataBaseProxyInvocationHandler``` в конструкторе открывает соединение с базой данных:
```java
public DataBaseProxyInvocationHandler(Object target, String url, String username, String password){
        this.target = target;
        try {
            connection = DriverManager.getConnection(url,username, password);
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS cache (args int[],data int[])";

            Statement statement = connection.createStatement();
            statement.execute(sqlCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
```
И если таблица с кешем не создана, он создает таковую. Далее мы проверяем 
имеется ли нужная аннотация на методе:
```java
if(method.isAnnotationPresent(Cacheable.class))
```
Если аннотация присутствует, делаем запрос базе данных:
```java
String sqlGetCacheByParameter = "SELECT data FROM cache WHERE args = ?";

PreparedStatement statement = connection.prepareStatement(sqlGetCacheByParameter);
statement.setArray(1, connection.createArrayOf("integer", args));

ResultSet resultSet = statement.executeQuery();

if (resultSet.next())
    return Arrays.asList(resultSet.getArray(1));
```
Мы делаем поиск по параметрам метода, для этого используем метод setArray (он интегрирует
массив в запрос), после выполняем запрос и получаем результат, и если присутствует следующий элемент,
возвращаем первую колонку (т.к. мы делали запрос только для data)</br>

Если же запрос не дал результата (он пуст), мы высчитываем результат работы метода,
и сохраняем его в базе данных, после возвращаем сам результат.

```java
Object[] result = ((List<Integer>) method.invoke(target, args)).toArray();
String sqlUpdateTableWithNewCache = "INSERT INTO cache (args,data) VALUES (?,?)";

statement = connection.prepareStatement(sqlUpdateTableWithNewCache);
statement.setArray(1, connection.createArrayOf("integer", args));
statement.setArray(2, connection.createArrayOf("integer", result));

statement.executeQuery();
```