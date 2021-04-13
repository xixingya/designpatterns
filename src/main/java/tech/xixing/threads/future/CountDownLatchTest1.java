package tech.xixing.threads.future;

import java.util.concurrent.*;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/4/12 7:10 下午
 */
public class CountDownLatchTest1 {

    public static void main(String[] args) throws InterruptedException {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 100, 2000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(30));
        CountDownLatch countDownLatch=new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep((long)(Math.random()*1000));
                    }catch (Exception e){

                    }finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        long start = System.currentTimeMillis();
        System.out.println("main thread is await the countDownLatch Thread to complete!");
        final boolean await = countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        if(!await){
            System.out.println("---------threads run timeout---------");
        }
        System.out.println("main thread is start running ,this thread use time ="+(System.currentTimeMillis()-start));
        threadPoolExecutor.shutdown();
    }
}
