package com.example;

/**
 * Created by jafir on 2017/3/23.
 */

public class TestJoin {


    /**
     * join方法的意思是     调用方法的线程将在被调用者执行完了之后才执行
     * main中调用 thread1.join() main里的方法只有在thread1执行完了之后才执行
     * @param aaa
     */
    public static void main(String [] aaa){

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    System.out.println("thread1 :"+i);
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread1.join();//在线程2中调用，表示线程2在thread1完成之后调用
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 10; i++) {
                    System.out.println("thread2 :"+i);
                }
            }
        });


        thread1.start();
        thread2.start();
        try {
            thread1.join();//join表示 这是在主线程调用的，那么主线程只有在thread1结束之后才能执行
            thread2.join();//join表示 这是在主线程调用的，那么主线程只有在thread2结束之后才能执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("main :"+i);
        }

    }
}
