package tech.xixing.threads.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author liuzhifei
 * @date 2022/1/25 3:25 下午
 */
public class PoolTest {

    public static void main(String[] args) throws InterruptedException {
        threadPoolTest();
    }


    public static void threadPoolTest() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                1000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNameFormat("qaq-%d").build(), (r, executor) -> {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        List<Integer> list = new ArrayList<>(20000);
        try {
            // 模拟200个请求
            for (int i = 0; i < 2000000000; i++) {
                final int num = i;
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "-结果:" + num);
                });
            }
        }catch (Exception e){
            System.out.println(e);
        }
//        while (Thread.activeCount()>2){
//            Thread.yield();
//        }
//        threadPoolExecutor.shutdown();
        System.out.println("线程执行结束");
    }
}
