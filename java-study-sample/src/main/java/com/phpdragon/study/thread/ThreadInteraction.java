package com.phpdragon.study.thread;

/**
 * 线程的交互
 * http://www.cnblogs.com/linjiqin/p/3208901.html
 */
public class ThreadInteraction {

    private static ThreadSum sum;

    public static void main(String[] args) {
        ThreadInteraction thread = new ThreadInteraction();

        sum = thread.new ThreadSum();
        sum.start();

        // 主线程要调用wait()方法，那么主线程必须拥有子线程的对象锁
        // 如果主线程不在同步方法或同步块里调用wait()方法，该方法抛出一个IllegalMonitorStateException异常。
        //
        // 所以主线程需要 使用synchronized 关键字获取子线程sum的对象锁,
        // 然后再调用wait()方法，阻塞的等待子线程发来的通知。
        synchronized (sum) {
            try {
                System.out.println("主线程等待对象sum完成计算...");

                //当前主线程等待
                //调用wait()方法后,主线程加入到子线程sum对象的监视器上，等待子线程发起通知后再继续往下执行
                sum.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("主线程得到sum对象计算的总和是：" + sum.total + ";  1阻塞");
        }

        //这段代码被阻塞执行,原因是上面的synchronized代码同步块么？
        System.out.println("主线程得到sum对象计算的总和是：" + sum.total + ";  2阻塞");
        System.out.println( "主线程执行样例代码 0 完毕");


        //##########################################################################

        System.out.println( "\n\n");
        sum = thread.new ThreadSum();

        //启动结果监听
        thread.new GetThreadSumResult().start();
        thread.new GetThreadSumResult().start();
        thread.new GetThreadSumResult().start();

        sum.start();

        //这段输出的顺序随机,表明是非阻塞执行的
        System.out.println( "主线程执行样例代码 1 完毕");
    }

    class GetThreadSumResult extends Thread{

        @Override
        public void run() {
            // 主线程要调用wait()方法，那么主线程必须拥有子线程的对象锁
            // 如果主线程不在同步方法或同步块里调用wait()方法，该方法抛出一个IllegalMonitorStateException异常。
            //
            // 所以主线程需要 使用synchronized 关键字获取子线程sum的对象锁,
            // 然后再调用wait()方法，阻塞的等待子线程发来的通知。
            synchronized (sum) {
                try {
                    System.out.println("子线程" + getName() +", 等待对象sum完成计算...");

                    //当前主线程等待
                    //调用wait()方法后,主线程加入到子线程sum对象的监视器上，等待子线程发起通知后再继续往下执行
                    sum.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("子线程" + getName() + ", 得到sum对象计算的总和是：" + sum.total + ";  1阻塞");
            }

            //这段代码被阻塞执行,原因是上面的synchronized代码同步块么？
            System.out.println("子线程" + getName() + ", 得到sum对象计算的总和是：" + sum.total + ";  2阻塞");
        }
    }

    class ThreadSum extends Thread {
        int total = 0;

        @Override
        public void run() {
            //synchronized (this) 获得当前对象锁
            synchronized (this) {
                for (int i = 0; i < 5; i++) {
                    total += i;
                    simulateSleep(1000);
                }

                //唤醒在此对象监视器上等待的所有线程, 如本示例的主线程、各子线程GetThreadSumResult
                //在多数情况下，建议使用notifyAll 而不是 notify
                notifyAll();
            }
        }
    }

    public static void simulateSleep(long millis) {
        millis = millis <= 0 ? 1000 : millis;

        long time = System.currentTimeMillis();
        // 使用while循环模拟 sleep
        while ((System.currentTimeMillis() - time < millis)) {
            //
        }
    }
}
