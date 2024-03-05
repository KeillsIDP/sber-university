package me.keills;

import java.util.List;

public interface Calculator {
    @Cacheable
    List<Integer> fibonachi(int n);
}
