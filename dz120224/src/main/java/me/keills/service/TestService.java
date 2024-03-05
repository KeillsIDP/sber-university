package me.keills.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestService implements Service{
    @Override
    public List<Integer> doWork(String name, int start) {
        System.out.println(name);
        return IntStream.range(start,1_000_000).boxed().collect(Collectors.toList());
    }

    @Override
    public List<Integer> doWork(String name, String smthg, int start) {
        System.out.println(name);
        return IntStream.range(start,1_000_000).boxed().collect(Collectors.toList());
    }

    @Override
    public List<Integer> doWorkToFile(String name, int start) {
        System.out.println(name);
        return IntStream.range(start,1_000_000).boxed().collect(Collectors.toList());
    }

    @Override
    public List<Integer> doWorkToFile(String name, String smthg, int start) {
        System.out.println(name);
        return IntStream.range(start,1_000_000).boxed().collect(Collectors.toList());
    }

    @Override
    public List<Integer> doWorkToZipFile(String name, int start) {
        System.out.println(name);
        return IntStream.range(start,1_000_000).boxed().collect(Collectors.toList());
    }

    @Override
    public Service notSerialized() {
        return this;
    }
}
