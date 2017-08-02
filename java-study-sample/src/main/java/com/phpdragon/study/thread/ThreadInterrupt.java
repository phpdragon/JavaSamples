package com.phpdragon.study.thread;

/**
 * 线程中断
 * http://www.cnblogs.com/onlywujun/p/3565082.html
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws Exception {
        ThreadInterrupt thread = new ThreadInterrupt();

        //样例0，子线程没有使用可以导致抛出中断异常的方法
        run(thread.new example0(), 0);

        //样例1，主线程发起了子线程中断，但子线程并没有中断执行？？？
        System.out.println("\n\n");
        run(thread.new example1(), 1);

        //样例2，子线程使用了可以导致抛出中断异常的方法,如 sleep、wait、join
        System.out.println("\n\n");
        run(thread.new example2(), 2);
    }

    private static void run(Thread thread, int num) throws Exception {
        System.out.println("子线程example" + num + "是否处于运行状态: " + thread.isAlive());

        thread.start();

        System.out.println("子线程example" + num + "是否处于运行状态: " + thread.isAlive());

        //主线程休眠2秒
        Thread.sleep(2000);
        System.out.println("主线程向子线程example" + num + "发起中断");
        //主线程向子线程发起中断
        thread.interrupt();

        thread.join();
        System.out.println("子线程example" + num + "是否处于运行状态: " + thread.isAlive());
    }

    /**
     * 中断样例0,代码中不使用会导致抛出中断异常(InterruptedException)的sleep、wait、join等方法。
     */
    class example0 extends Thread {
        @Override
        public void run() {
            int n = 0;
            // 每隔一秒检测是否设置了中断标示
            while (!Thread.currentThread().isInterrupted()) {
                n++;
                System.out.println("子线程example0执行:" + n);

                //模拟 sleep
                ThreadInterrupt.simulateSleep(1000);
            }
        }
    }

    /**
     * 中断样例1,该子线并不会中断执行
     */
    class example1 extends Thread {
        @Override
        public void run() {
            int n = 0;
            while (!Thread.currentThread().isInterrupted()) {
                n++;

                System.out.println("子线程example1执行:" + n);

                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("子线程example1捕捉到中断异常: InterruptedException ," + e.getMessage());
                }
            }
        }
    }

    /**
     * 中断样例1,代码中使用会导致抛出中断异常(InterruptedException)的sleep、wait、join等方法。
     */
    class example2 extends Thread {
        @Override
        public void run() {
            int n = 0;
            while (!Thread.currentThread().isInterrupted()) {
                n++;

                System.out.println("子线程example2执行:" + n);

                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    //http://www.cnblogs.com/w-wfy/p/6414801.html
                    //nterrupt方法是用于中断线程的，调用该方法的线程的状态将被置为"中断"状态。
                    // 注意：线程中断仅仅是设置线程的中断状态位，不会停止线程。
                    // 所以当一个线程处于中断状态时，如果再由wait、sleep以及jion三个方法引起的阻塞，
                    // 那么JVM会将线程的中断标志重新设置为false，并抛出一个InterruptedException异常，
                    // 然后开发人员可以中断状态位“的本质作用-----就是程序员根据try-catch功能块捕捉jvm抛出的InterruptedException异常来做各种处理，
                    // 比如如何退出线程。总之interrupt的作用就是需要用户自己去监视线程的状态位并做处理。”

                    System.out.println("子线程example2捕捉到中断异常: InterruptedException ," + e.getMessage());

                    //http://www.cnblogs.com/onlywujun/p/3565082.html
                    //中不中断由自己决定，如果需要真真中断线程，则需要重新设置中断位。
                    //如果不需要中断子线程，则不用调用
                    Thread.currentThread().interrupt();
                }
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
