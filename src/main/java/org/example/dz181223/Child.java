package org.example.dz181223;

public class Child extends Parent{

    static {
        System.out.println("Child:static 1");
    }

    static {
        System.out.println("Child:static 2");
    }

    {
        System.out.println("Child:instance 1");
    }

    {
        System.out.println("Child:instance 2");
    }

    public Child(){
        System.out.println("Child: constructor");
    }

    public Child(String name){
        // т.к. name - private мы не можем использовать его в Child
        // но мы можем вызвать конструктор родителя и передать туда параметр, но нам это ничего не даст
        // super(name);
        System.out.println("Child:name-constructor");
        System.out.println("Child:"+name+"-constructor");

    }
}
