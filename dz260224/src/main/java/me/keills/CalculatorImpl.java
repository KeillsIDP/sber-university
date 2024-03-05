package me.keills;

import java.util.ArrayList;
import java.util.List;

public class CalculatorImpl implements Calculator{
    @Cacheable
    public List<Integer> fibonachi(int n) {
        List<Integer> list = new ArrayList<>();

        int f = 0;
        int s = 1;
        int i = 0;
        while(i<n){
            list.add(f);

            int next = f + s;
            f = s;
            s = next;

            i++;
        }

        return list;
    }
}