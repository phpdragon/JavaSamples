package com.phpdragon.study.thread;

/**
 * 线程的生命周期,http://www.cnblogs.com/linjiqin/p/3208494.html
 * <p>
 * 线程可以分为4个状态：
 * New(新生)，
 * Runnable(可运行)：为了方便分析，还可将其分为：Runnable与Running。
 * blocked(被阻塞)，
 * Dead(死亡)。
 */
public class ThreadLifeCycle extends Thread {

    public static void main(String[] args) throws Exception {
        ThreadLifeCycle thread = new ThreadLifeCycle();

        System.out.println("new ThreadLifeCycle(), 子线程处于等待状态");
        System.out.println("子线程是否处于运行状态: " + thread.isAlive());
        System.out.println("exec thread.start(), 子线程进入运行状态");

        thread.start();

        System.out.println("子线程是否处于运行状态: " + thread.isAlive());
        System.out.println("exec thread.join(), 主线程“加入”到子线程的尾部。在子线程执行完毕之前，主线程不能继续往下执行");

        //thread.join(); // 等子线程执行完毕后，再继续往下执行后续代码, http://www.cnblogs.com/jpwz/p/6248000.html
        thread.join(4000); // 设置等待超时时间4秒，获取不到子线程的锁后继续往下执行

        System.out.println("子线程已经结束");
        System.out.println("子线程是否处于运行状态: " + thread.isAlive());
    }

    public void run() {
        int n = 0;
        while ((++n) <= 5) {
            System.out.println("子线程执行:" + n);
            try {
                System.out.println("子线程休眠1秒");
                this.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
