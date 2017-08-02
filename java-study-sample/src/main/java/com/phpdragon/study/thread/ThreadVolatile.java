package com.phpdragon.study.thread;

/**
 * volatile 关键字慎用。
 * 1.建议用该关键字来定义boolean标记变量、通知变量，因为volatile关键字变量，修改时对任何线程立即可见。
 * 2.不建议在有多线程对该变量进行计算的场景使用。
 * 3.不建议使用有如下，当变量的值由自身的上一个决定时(n = n + 1、n++、n--)的场景.
 * 只有当变量的值和自身上一个值无关时对该变量的操作才是原子级别的，如n = m + 1，这个就是原子级别的
 */
public class ThreadVolatile extends Thread{
    public static volatile int m = 0;
    public static volatile int n = 0;

    public static synchronized void inc() {
        n++;
    }

    public void run() {
        for (int i = 0; i < 10; i++)
            try {
                m += 1;
                inc();
                sleep(3); // 为了使运行结果更随机，延迟3毫秒
            } catch (Exception e) {
            }
    }

    public static void main(String[] args) throws Exception {
        Thread threads[] = new Thread[10];

        for (int i = 0; i < threads.length; i++)
            // 建立100个线程
            threads[i] = new ThreadVolatile();
        for (int i = 0; i < threads.length; i++)
            // 运行刚才建立的100个线程
            threads[i].start();
        for (int i = 0; i < threads.length; i++)
            // 100个线程都执行完后继续
            threads[i].join();

        //执行发现，m不等于 100，n等于100
        System.out.println("m=" + ThreadVolatile.m);
        System.out.println("n=" + ThreadVolatile.n);
    }
}
