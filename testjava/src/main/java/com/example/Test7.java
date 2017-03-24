package com.example;

/**
 * Created by jafir on 2017/3/22.
 */

public class Test7 {
    public static int j = Test6.i;

    static {
        j++;
    }

    {
        j++;
    }
}
