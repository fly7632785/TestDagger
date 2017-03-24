package com.example;

/**
 * Created by jafir on 2017/3/22.
 */

public class TestIntern {


    public static void main(String[] ccc) {


        String a = "aaaa";
        String b = "aaaa";
        //因为aaa在常量池里面，a和b变量都引用它所以相等
        System.out.println("isEquatl:" + (a == b));

        String c = new String("bbbb");
        String d = "bbbb";
        //因为bbbb在常量池里面，d变量的bbbb在堆里面，c和d变量的引用不相等
        System.out.println("isEquatl:" + (c == d));


        String f = "cccc";
        String e = new String("cccc");
        //这里吧cccc放到常量池,所以e f都引用在cccc上
        e = e.intern();//true
//        e.intern();//false
//        f = f.intern();//false
        System.out.println("isEquatl:" + (e == f));





        String s = new String("1");
        String s2 = "1";
        s.intern();
        System.out.println(s == s2);

        String s3 = new String("1") + new String("1");
        String s4 = "11";
        s3.intern();
        System.out.println(s3 == s4);

    }
}
