package org.example.dz181223;

public class Parent {
    private String name;

    static {
        System.out.println("Parent:static 1");
    }

    static {
        System.out.println("Parent:static 2");
    }

    {
        System.out.println("Parent:instance 1");
    }

    {
        System.out.println("Parent:instance 2");
    }

    public Parent(){
        System.out.println("Parent: constructor");
    }

    public Parent(String name){
        System.out.println("Parent:name-constructor");
        System.out.println("Parent:"+name+"-constructor");
        this.name = name;
    }
}
