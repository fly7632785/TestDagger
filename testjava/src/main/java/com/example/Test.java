package com.example;

/**
 * Created by jafir on 2017/3/22.
 */

public class Test {



    public Test(){

    }

    //初始化块（静态）
    static {

        Field = 1;
    }
    public static int Field = 3;
    //初始化块
    int fiel2 = 10;
    {
        Field = 2;
        fiel2 = 11;
    }

    public static void main(String[] args) {

        System.out.println("字段Field的值是【" + Field + "】");
        Test test = new Test();
        System.out.println("test【" + test.fiel2 + "】");

        /*************************************\
         * 输出结果
         * 字段Field的值是【1】
         \*************************************/
    }
}
