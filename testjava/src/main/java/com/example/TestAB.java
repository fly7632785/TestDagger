package com.example;

/**
 * Created by jafir on 2017/3/22.
 */

public class TestAB {

    public static final String A; // 常量A
    public static final String B;    // 常量B
    static {
        /**
         * 虽然这里有赋值  但是不是真正意义上的赋值
         * 只有在方法内部再赋值的时候才会报错 不准赋值 final
         *
         */
        A = "ab";
        B = "cd";
    }
    public static void main(String[] args) {
// 将两个常量用+连接对s进行初始化

        String s = A + B;
        String t = "abcd";

        if (s == t) {
            System.out.println("s等于t，它们是同一个对象");
        } else {
            System.out.println("s不等于t，它们不是同一个对象");
        }




    }






}
