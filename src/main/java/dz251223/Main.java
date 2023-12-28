package dz251223;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        CountMap<Integer> countMap = new CountMapImpl<>();
        countMap.add(10);
        countMap.add(10);
        Map<Integer,Integer> m = new HashMap<>();
        countMap.toMap(m);
        System.out.println(m);
    }
}
