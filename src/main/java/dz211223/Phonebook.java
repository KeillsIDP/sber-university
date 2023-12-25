package dz211223;

import java.util.*;

public class Phonebook {
    private Map<String, List<String>> container;

    public Phonebook(){
        this.container = new HashMap<>();
    }

    public void add(String lastName, String phoneNumber){
        // можем не переприсваивать значение элементу, т.к. лист - ссылочный тип
        if(container.containsKey(lastName))
            container.get(lastName).add(phoneNumber);
        else
            container.put(lastName, new ArrayList<>(Arrays.asList(phoneNumber)));
    }

    public void addAll(String lastName, List<String> phoneNumber){
        if(container.containsKey(lastName))
            container.get(lastName).addAll(phoneNumber);
        else
            container.put(lastName, phoneNumber);
    }

    public List<String> get(String lastName){
        // если нет элемента по заданному ключу, то возвращаем пустой лист
        if(container.containsKey(lastName))
            return container.get(lastName);
        else
            return new ArrayList<>();
    }

    @Override
    public String toString(){
        return container.toString();
    }
}
