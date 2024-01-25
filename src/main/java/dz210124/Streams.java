package dz210124;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> {
    private List<T> list;

    public List<T> getList() {
        return List.copyOf(list);
    }

    private Streams(List<T> list) {
        this.list = new ArrayList<>(list);
    }

    public static <T> Streams<T> of(List<T> list) {
        return new Streams<>(list);
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        List<T> newList = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t)) {
                newList.add(t);
            }
        }
        list = newList;
        return this;
    }

    public <R> Streams<R> transform(Function<? super T, ? extends R> mapper) {
        List<R> newList = new ArrayList<>();
        for (T t : list) {
            newList.add(mapper.apply(t));
        }
        return new Streams<>(newList);
    }

    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        Map<K, V> map = new HashMap<>();
        for (T t : list) {
            map.put(keyMapper.apply(t), valueMapper.apply(t));
        }
        return map;
    }

    @Override
    public String toString(){
        return list.toString();
    }
}
