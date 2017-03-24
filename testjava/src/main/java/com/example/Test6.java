package com.example;

/**
 * Created by jafir on 2017/3/22.
 */

public class Test6 {
    public static int i = Test7.j;

    {
        i++;
    }

    static {
        i = 0;
    }

    public Test6() {
        i++;
    }
}
