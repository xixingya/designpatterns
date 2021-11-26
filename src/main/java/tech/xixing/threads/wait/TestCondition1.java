package tech.xixing.threads.wait;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class TestCondition1 {

    static ReentrantLock lock = new ReentrantLock();//锁对象
    static boolean isWoman = false; // 是否有女人
    static boolean isMoney = false; // 是否加钱了
    //因为钱不满足而进入阻塞的队列
    static Condition conditionMoney = lock.newCondition();
    //因为女人不满足而进入阻塞的队列
    static Condition conditionWoman = lock.newCondition();


    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {//jack
            lock.lock();
            while (!isWoman){//判断是否有女人
                log.debug("没有女人 我去等待老板安排 先休息，安排好之后叫醒我");
                try {

                    //jack线程进入阻塞，但是释放了锁
                    //进入特定的队列---conditionWoman
                    conditionWoman.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("开始工作啪啪啪啪");
            lock.unlock();
        }, "jack").start();

        new Thread(() -> {
            lock.lock();
            while (!isMoney){
                log.debug("没有加钱，先休息不干活");
                try {
                    // 进入特定的队列---conditionWoman
                    conditionMoney.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("干活了----有钱能使鬼推");
            lock.unlock();
        }, "rose").start();



        TimeUnit.SECONDS.sleep(5);
        new Thread(() -> {//jack
            lock.lock();
            isWoman=true;
            log.debug("jack 桥本有菜来了，你可以啪啪啪了");
            log.debug("因为是女人满足，从特定的队列当中（conditionWoman）唤醒");
            conditionWoman.signalAll();
            lock.unlock();
        }, "boss").start();

    }
}
