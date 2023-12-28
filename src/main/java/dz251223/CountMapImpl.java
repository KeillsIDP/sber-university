package dz251223;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CountMapImpl<K> implements CountMap<K>{
    Map<K,Integer> container = new HashMap<>();

    @Override
    public void add(K o) {
        if(container.putIfAbsent(o,1)==null)
            container.put(o,container.get(o)+1);
    }

    @Override
    public int getCount(K o) {
        return (int)container.entrySet().stream().filter(x->x.equals(o)).count();
    }

    @Override
    public int remove(K o) {
        return container.remove(o);
    }

    @Override
    public int size() {
        return container.size();
    }

    @Override
    public void addAll(CountMap<? extends K> source) {
        container.putAll(source.toMap());
    }

    @Override
    public Map<K,Integer> toMap() {
        return container;
    }

    @Override
    public void toMap(Map<K,Integer> destination) {
        destination.clear();
        destination.putAll(container);
    }
}
