package tech.xixing.threads.wait;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @link https://blog.csdn.net/java_lyvee/article/details/110996764
 */
@Slf4j
public class TestReentrantLock {

    static List<Thread> threadList = new ArrayList<>();

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                lock.lock();
                try {
                    log.debug("thread execute threadName={}", Thread.currentThread().getName());
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }


            }, "t" + i);
            threadList.add(t);
        }

        log.debug("------启动顺序，调度顺序如果是顺序的，那么应该是正序0-9---------");

        lock.lock();
        try {
            for (Thread thread : threadList) {
                log.debug("-------启动顺序-------{}", thread.getName());
                thread.start();
                TimeUnit.MILLISECONDS.sleep(1);
            }
            log.debug("-----执行顺序---------，按道理应该是0-9  ------------");
        } finally {
            lock.unlock();
        }

    }
}
