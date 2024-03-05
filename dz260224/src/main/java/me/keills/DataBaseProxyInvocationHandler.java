package me.keills;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseProxyInvocationHandler implements InvocationHandler {

    private final Object target;
    private final Connection connection;
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

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if(method.isAnnotationPresent(Cacheable.class)){
            try {
                String sqlGetCacheByParameter = "SELECT data FROM cache WHERE args = ?";

                PreparedStatement statement = connection.prepareStatement(sqlGetCacheByParameter);
                statement.setArray(1, connection.createArrayOf("integer", args));

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next())
                    return Arrays.asList(resultSet.getArray(1));

                Object[] result = ((List<Integer>) method.invoke(target, args)).toArray();
                String sqlUpdateTableWithNewCache = "INSERT INTO cache (args,data) VALUES (?,?)";

                statement = connection.prepareStatement(sqlUpdateTableWithNewCache);
                statement.setArray(1, connection.createArrayOf("integer", args));
                statement.setArray(2, connection.createArrayOf("integer", result));

                statement.executeQuery();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                return method.invoke(target, args);
            }
        }
        return method.invoke(target, args);
    }
}
