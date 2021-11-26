package tech.xixing.threads.wait;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class TestCondition2 {

    static ReentrantLock lock = new ReentrantLock();//锁对象
    static boolean isWoman = false; // 是否有女人
    static Condition conditoon = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {//jack
            lock.lock();
            while (!isWoman) {//判断是否有女人
                log.debug("没有女人 我去等待老板安排 先休息，安排好之后叫醒我");
                try {

                    //jack线程进入阻塞，但是释放了锁
                    conditoon.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("开始工作啪啪啪啪");
            lock.unlock();
        }, "jack").start();


        TimeUnit.SECONDS.sleep(1);

        log.debug("1s之后主线程获取锁");
        log.debug("因为jack wait释放了锁，主线程能够获取到");
        log.debug("-----------关键看打印顺序----------------");
        lock.lock();

//        //注意顺序换了------------------------
//        isWoman = true;
//        conditoon.signalAll();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lock.lock();
                log.debug("那些默默无闻的程序员coding");
                lock.unlock();
            }, "t" + i).start();
            TimeUnit.MILLISECONDS.sleep(1);
        }

        //注意顺序换了------------------------
        isWoman = true;
        conditoon.signalAll();



        lock.unlock();


    }
}
