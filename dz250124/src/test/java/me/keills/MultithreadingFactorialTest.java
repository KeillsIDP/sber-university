package me.keills;

import org.apache.velocity.runtime.parser.node.MathUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultithreadingFactorialTest {

    @Test
    void find_factorial(){
        generateNumbers();
        List<Integer> list = readNumbers();

        for(int i=0;i<list.size();i++){
            MultithreadingFactorial runnable = new MultithreadingFactorial(list.get(i));
            Thread thread = new Thread(runnable);
            thread.run();

            assertTrue(runnable.getResult()==factorial(list.get(i)));
            System.out.println(runnable.getResult());
        }

    }

    long factorial(int num){
        long result = 1;
        for(int i = 1;i<=num;i++)
            result*=i;
        return result;
    }

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
}