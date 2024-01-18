package dz110124;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class ArrayIteratorImpl <T> implements Iterator {

    public T[] container;
    public int index = -1;

    public ArrayIteratorImpl(T[] container){
        this.container = container;
    }

    @Override
    public boolean hasNext() {
        return index<container.length-1;
    }

    @Override
    public Object next() throws NoSuchElementException{
        if(hasNext())
            return container[++index];
        throw new NoSuchElementException("Вы проитерировали все элементы");
    }
}
