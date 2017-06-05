package com.example;

/**
 * Created by jafir on 2017/4/11.
 */
public class TestJava {
    public static void main(String[] args) {
        Other other = new Other();
        new TestJava().addOne(other);
        System.out.println(other.i);
    }
    public void addOne(final Other other) {
        other.i++;
    }
}

class Other {
    public int i;
}