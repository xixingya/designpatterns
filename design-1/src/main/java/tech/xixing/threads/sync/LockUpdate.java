package tech.xixing.threads.sync;

import java.util.stream.IntStream;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author liuzhifei
 * @date 2022/2/7 5:11 下午
 */
public class LockUpdate {

    static Object yesLock;


    public static void main(String[] args) throws InterruptedException {
        yesLock=new Object();
        Thread.sleep(5000L);
        final Thread yesLock = getYesLock();
        Thread.sleep(2000L);
        //System.out.println(yesLock.isAlive());
        //System.out.println("no lock object" + ClassLayout.parseInstance(yesLock).toPrintable());
        getYesLock();
        Thread.sleep(2000L);

        IntStream.rangeClosed(1,4).forEach(i->getYesLock());
        Thread.sleep(5000L);
        System.out.println("无竞争后锁状态：" + ClassLayout.parseInstance(LockUpdate.yesLock).toPrintable());
        getYesLock();
    }

    private static Thread getYesLock(){
        final Thread thread = new Thread(() -> {
            try {
                synchronized (yesLock) {
                    System.out.println("线程[" + Thread.currentThread().getName() + "]" +
                            "锁状态" + ClassLayout.parseInstance(yesLock).toPrintable());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        return thread;
    }
}
