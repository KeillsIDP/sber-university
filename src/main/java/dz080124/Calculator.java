package dz080124;

import dz080124.annotation.Metric;

public interface Calculator {
    /**
     * Расчет факториала числа.
     * @param number
     */
    @Metric
    int calc (int number);
}
