package dz210124;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StreamsTest {

    List<Integer> intList = Arrays.asList(1,2,3,4,5);

    @Test
    void of() {
        Streams<Integer> stream = Streams.of(intList);
        assertTrue(stream.getList().size()==5);
    }

    @Test
    void filter() {
        Streams<Integer> stream = Streams.of(intList).filter(x->x>=3);
        assertTrue(stream.getList().size()==3);
        for(Integer num: stream.getList())
            assertTrue(num>=3);
    }

    @Test
    void transform() {
        Streams<Integer> stream = Streams.of(intList).transform(x->x+3);
        for(int i = 0;i<stream.getList().size();i++)
            assertTrue(intList.get(i)+3==stream.getList().get(i));
    }

    @Test
    void toMap() {
        Map<Integer,String> stream = Streams.of(intList).toMap(x->x%2,Object::toString);
        assertTrue(stream.size()==2);
    }
}