package com.example;

/**
 * Created by jafir on 2017/3/22.
 */

public class Test1 {

    public static void main(String[] args) {

        Test7 t7 = new Test7();
        System.out.println("字段I的值是【" + Test6.i + "】");
        System.out.println("字段J的值是【" + Test7.j + "】");



        int a = 10 ,b = 3;
        int  i = a/b+a%b!=0?1:0;
        System.out.print(i);

//        boolean is = true;
//        int k = 100 - is?20:0-44;
//       System.out.print(k);

    }
}
