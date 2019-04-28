package Reflection.Examples;

import Reflection.Example;
import Reflection.Writable;

public class Tree implements Example {

    @Writable
    private String kind;
    @Writable
    private int age;

    private int height;

    public Tree(String kind, int age, int height) {
        this.kind = kind;
        this.age = age;
        this.height = height;
    }
}
