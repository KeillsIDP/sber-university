package dz281223.utils;

import java.util.Random;

public class RandomPinGenerator {
    public static String generate(){
        Random rand = new Random();
        String pin = "";

        for(int i  = 0; i < 4; i++){
            int generated = rand.nextInt(10);
            pin += (char)(48 + generated);
        }

        return pin;
    }
}
