package tech.xixing.threads.wait;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


@Slf4j
public class TestWait1 {

    static  Object object = new Object();//锁对象
    static boolean isWoman = false; // 是否有女人
    static boolean isMoney = false; // 是否加钱了

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




        new Thread(() -> {
            synchronized (object){
                while (!isMoney){
                    log.debug("没有加钱，先休息不干活");
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("干活了----有钱能使鬼推");
            }
        }, "rose").start();


//        for (int i = 0; i <5 ; i++) {
//            new Thread(() -> {
//                synchronized (object){
//                    log.debug("那些默默无闻的程序员coding");
//                }
//            }, "程序员"+i).start();
//        }



        //这里睡眠主要是为了视觉效果，没什么意义
        //5s之后叫醒jack
        TimeUnit.SECONDS.sleep(5);
        new Thread(() -> {//jack
            synchronized (object){
                isWoman=true;
                log.debug("jack 桥本有菜来了，你可以啪啪啪了");
                object.notifyAll();
            }
        }, "boss").start();

    }
}
