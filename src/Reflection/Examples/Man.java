package Reflection.Examples;

import Reflection.Example;
import Reflection.Writable;

public class Man implements Example {

    @Writable
    private String name;
    @Writable
    private int age;

    private int weight;
    private int height;
    private String gender;

    public Man(String name, int age, int weight, int height, String gender) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }
}
