package tech.xixing.threads.future;

import java.util.concurrent.CompletableFuture;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/4/12 10:34 上午
 */
public class CompletableFutureTest1 {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Double> completableFuture=CompletableFuture.supplyAsync(CompletableFutureTest1::fetchPrice);
        completableFuture.thenAccept((result)->{
            System.out.println(result);
        });
        completableFuture.exceptionally(e->{
            e.printStackTrace();
            return null;
        });
        Thread.sleep(20000);

    }
    public static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }
}
