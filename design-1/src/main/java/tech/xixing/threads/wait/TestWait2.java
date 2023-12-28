package tech.xixing.threads.wait;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 * 解释一下代码，首先jack获取到了锁，然后调用了wait方法进入了waitSet阻塞，
 * 跟着主线程获取了锁，主线程获取锁之后启动了5个线程(t0-t4)；这5个线程也要获取锁，
 * 但是因为现在锁被主线程持有所以这五个线程肯定是获取不到锁的，继而进入EntryList阻塞；
 * 启动完5个线程之后主线程满足条件isWoman=true;从而唤醒jack如果jack是即时唤醒执行的话，
 * 那么打印的顺序肯定是jack先执行（诚然结果也是jack先执行，但是其实这并不能说明notifyAll是即时唤醒），
 * 然后是t4-t0执行；但是笔者讲过notifyAll其实是转移线程到EntryList，
 * 也就是说当主线程调用完object.notifyAll();之后EntryList当中应该存在6个线程 t0、t1、t2、t3、t4、jack
 * ————————————————
 * 版权声明：本文为CSDN博主「shadow?s」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/java_lyvee/article/details/110996764
 */
@Slf4j
public class TestWait2 {

    static  Object object = new Object();//锁对象
    static boolean isWoman = false; // 是否有女人

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {//jack
            synchronized (object){
                while (!isWoman){//判断是否有女人
                    log.debug("没有女人 我去等待老板安排 先休息，安排好之后叫醒我");
                    try {
                        //jack线程进入阻塞，但是释放了锁
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("开始工作啪啪啪啪");
            }
        }, "jack").start();


        TimeUnit.SECONDS.sleep(1);

        log.debug("1s之后主线程获取锁");
        log.debug("因为jack wait释放了锁，主线程能够获取到");
        log.debug("-----------关键看打印顺序----------------");
        synchronized (object) {

            for (int i = 0; i < 5; i++) {
                new Thread(() -> {
                    synchronized (object) {
                        log.debug("那些默默无闻的程序员coding");
                    }
                }, "t" + i).start();
                TimeUnit.MILLISECONDS.sleep(1);
            }

            isWoman=true;
            //把这个放到前面后，jack就会被放到最后面执行，因为 TestSynchronized
            object.notifyAll();
        }


    }
}
