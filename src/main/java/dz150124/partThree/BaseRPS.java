package dz150124.partThree;

import java.util.Random;

public class BaseRPS {
    public int getResult(){
        Random rand = new Random();
        return rand.nextInt(3);
    }
}
