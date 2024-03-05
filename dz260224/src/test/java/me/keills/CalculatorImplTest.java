package me.keills;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorImplTest {
    private static final String db_url = "jdbc:postgresql://localhost:5432/sber";
    private static final String db_username = "postgres";
    private static final String db_password = "postgres";
    private static Calculator calcProxy;

    @BeforeEach
    void initCache(){
        Calculator calc = new CalculatorImpl();
        calcProxy = (Calculator) Proxy.newProxyInstance(
                calc.getClass().getClassLoader(),
                calc.getClass().getInterfaces(),
                new DataBaseProxyInvocationHandler(calc,db_url, db_username, db_password)
        );

        calcProxy.fibonachi(5);
        calcProxy.fibonachi(10);
        calcProxy.fibonachi(15);
    }

    @Test
    void test_cached() {
        Calculator calculator = new CalculatorImpl();
        assertEquals(calcProxy.fibonachi(5),calculator.fibonachi(5));
        assertEquals(calcProxy.fibonachi(10),calculator.fibonachi(10));
        assertEquals(calcProxy.fibonachi(15),calculator.fibonachi(15));
    }

    @Test
    void test_uncached() {
        Calculator calculator = new CalculatorImpl();
        assertEquals(calcProxy.fibonachi(4),calculator.fibonachi(4));
        assertEquals(calcProxy.fibonachi(9),calculator.fibonachi(9));
        assertEquals(calcProxy.fibonachi(14),calculator.fibonachi(14));
    }
}