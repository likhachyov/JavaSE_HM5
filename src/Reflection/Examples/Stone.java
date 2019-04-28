package Reflection.Examples;

import Reflection.Example;
import Reflection.Writable;

public class Stone implements Example {

    @Writable
    private String color;
    @Writable
    private int ages;

    private int weight;
    private String form;

    public Stone(String color, int ages, int weight, String form) {
        this.color = color;
        this.ages = ages;
        this.weight = weight;
        this.form = form;
    }
}
