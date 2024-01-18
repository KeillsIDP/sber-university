package dz150124.partOne.plugins;

import dz150124.partOne.Plugin;

public class SecondTestPlugin implements Plugin {

    @Override
    public void doUsefull() {
        System.out.println("Я плагин, который был написан вторым и тоже изменен!");
    }
}
