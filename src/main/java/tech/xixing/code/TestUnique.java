package tech.xixing.code;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liuzhifei
 * @date 2022/8/10 7:22 下午
 */
public class TestUnique {

    private static final Map<String, String> ID_LOCK = new HashMap<>();

    private static final Object LOCK = new Object();

    private static final AtomicLong WAIT_COUNT = new AtomicLong();


    public static void main(String[] args) throws InterruptedException {

        final ExecutorService executorService = Executors.newFixedThreadPool(200);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        final long countStart = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            executorService.submit(() -> {
                for (int k = 0; k < 100; k++) {
                    final Set<String> idList = getRandomIdList(2000);
                    try {
                        //获取id的锁
                        synchronized (LOCK) {
                            while (!lockId(idList)) {
                                //锁冲突，等待被唤醒，重试
                                WAIT_COUNT.incrementAndGet();
                                //System.out.println("wait a moment");
                                LOCK.wait();
                            }
                        }
                        // run
                        Thread.sleep(10);

                        //释放所有id的锁
                        synchronized (LOCK) {
                            remove(idList);
                            //唤醒等待线程
                            LOCK.notifyAll();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        executorService.shutdown();

        System.out.println(WAIT_COUNT);
        System.out.println("总耗时：" + (System.currentTimeMillis() - countStart));
    }

    public static boolean lockId(Set<String> idList) {
        final String name = Thread.currentThread().getName();
        //拥有锁
        Set<String> ownerIdSet = new HashSet<>(idList.size());
        for (String id : idList) {
            final String idOwner = ID_LOCK.get(id);
            if (Objects.isNull(idOwner)) {
                ID_LOCK.put(id, name);
                ownerIdSet.add(id);
            } else if (idOwner.equals(name)) {
                ID_LOCK.put(id, name);
                ownerIdSet.add(id);
            } else {
                //锁冲突
                break;
            }
        }

        if (ownerIdSet.size() != idList.size()) {
            //如果锁没有获取全，释放拥有的锁
            remove(ownerIdSet);
            //返回失败
            return false;
        }
        //获取锁成功
        return true;
    }

    public static void remove(Collection<String> idList) {
        for (String id : idList) {
            ID_LOCK.remove(id);
        }
    }

    public static final Random random = new Random();

    public static Set<String> getRandomIdList(int num) {
        final HashSet<String> list = new HashSet<>(num);
        for (int i = 0; i < num; i++) {
            list.add(getRandomId());
        }
        return list;
    }

    public static String getRandomId() {
        return random.nextInt(600000) + "";
    }

}
