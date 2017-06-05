package com.example;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by jafir on 2017/5/2.
 */

public class TestFuture {
    public static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            String tid = String.valueOf(Thread.currentThread().getId());
            System.out.printf("Thread#%s : in call\n", tid);
            return tid;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        List<Future<String>> results = new ArrayList<Future<String>>();
//        ExecutorService es = Executors.newCachedThreadPool();
//        for(int i=0; i<100;i++)
//            results.add(es.submit(new Task()));
//
//        for(Future<String> res : results)
//            System.out.println(res.get());
        Callable<Integer> callable = new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(5000);// 可能做一些事情
                System.out.println("33");
                return new Random().nextInt(100);
            }
        };
        FutureTask<Integer> future = new FutureTask<Integer>(callable);
        new Thread(future).start();
        try {
            System.out.println("11");
            Thread.sleep(2000);// 可能做一些事情
            System.out.println("22");
            System.out.println(future.get());//阻塞后面的代码
            System.out.println("44");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    int fibc(int num){
        if(num == 0){
            return 0;
        }

        if(num == 1){
            return 1;
        }

        return fibc(num-1)+fibc(num-2);
    }
}
