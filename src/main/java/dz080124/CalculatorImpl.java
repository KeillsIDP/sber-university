package dz080124;

import dz080124.annotation.Metric;

public class CalculatorImpl implements Calculator{
    @Override
    public int calc(int number) {
        int res = 1;
        for(int i = 1; i < number; i++)
            res*=i;
        return res;
    }

    private <T extends Number> double add(T first,T second){
        return first.doubleValue() + second.doubleValue();
    }

    private void getTest(){

    }
}
