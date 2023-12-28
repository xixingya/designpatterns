package tech.xixing.threads.wait;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *   			 来解释一下为什么需要保证这个调度顺序呢?
 *              这里所有代码的意图就是顺序启动线程(顺序调度线程)，这些线程启动之后会去拿锁(lock)
 *              肯定拿不到，因为这个时候锁被主线程持有
 *              主线程还在for循环没有释放锁，所以在for循环里面启动的线程都是拿不到锁的
 *              那么这些那不到锁的线程就会阻塞
 *              也就t0----t9阳塞之后他们被存到了一个队列当中
 *              这个JVM的源码中可以证明，我后面给大家看源码，
 *              总之你现在记住所有拿不到锁的线程会阻塞进入到Entrylist这个队列当中
 *              然后主线程执行完for循环后会释放放锁
 *              继而会去这个队列当中去唤醒一个个线程————随机唤醒还是顺序唤醒呢?
 *              假设是顺序唤醒，是倒序还是正序唤醒呢?
 *              需要证明这个问题，就要保证所有因为拿不到锁而进入到这个队列当中的线程
 *              他们的顺序必须是有序的，这样后面从他们的执行结果才能分析；
 *              假设你 进入到阻塞队列的时候都是随机的，那么后面唤醒线程执行的时候必然也是随机的
 *              那么则无法证明唤醒是否具备有序性
 *              为了保证进入到队列当中的线程调度是有序的，主线程睡眠很有必要
 *              那么为什么主线程睡眠1下就能保证这些线程的顺序调度呢?这个问题读者可以思考一下后而我会重点分析
 *              好了现在我们来看结果
 *
 *  @link https://blog.csdn.net/java_lyvee/article/details/110996764
 */
@Slf4j
public class TestSynchronized {
    static List<Thread> threadList = new ArrayList<>();

    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(()->{
                synchronized (lock){
                    log.debug("thread execute threadName={}",Thread.currentThread().getName());
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"t"+i);
            threadList.add(t);
        }

        log.debug("------启动顺序，调度顺序如果是顺序的，那么应该是正序0-9---------");
        synchronized (lock){
            for (Thread thread : threadList) {
                log.debug("-------启动顺序-------{}",thread.getName());
                thread.start();
                TimeUnit.MILLISECONDS.sleep(1);
            }
            log.debug("-----执行顺序---------，按道理应该是9-0------------");
        }
    }
}
