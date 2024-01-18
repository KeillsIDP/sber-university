package dz110124;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayIteratorImplTest {
    private ArrayIteratorImpl<Integer> iterator;
    private Integer[] container;

    @BeforeEach
    public void setUp() {
        container = new Integer[]{1, 2, 3};
        iterator = new ArrayIteratorImpl<>(container);
    }

    @Test
    public void testHasNext_True() {
        assertTrue(iterator.hasNext());
    }
    @Test
    public void testHasNext_False() {
        iterator.next();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
    }
    @Test
    public void testNext() {
        assertEquals(Integer.valueOf(1), iterator.next());
        assertEquals(Integer.valueOf(2), iterator.next());
        assertEquals(Integer.valueOf(3), iterator.next());
        try {
            iterator.next();
            fail("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            assertEquals("Вы проитерировали все элементы", e.getMessage());
        }
    }

    @Test
    public void testNext_NoSuchElementException() {
        while(iterator.hasNext())
            iterator.next();
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            assertEquals("Вы проитерировали все элементы", e.getMessage());
        }
    }
}