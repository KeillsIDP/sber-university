package me.keills;

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
