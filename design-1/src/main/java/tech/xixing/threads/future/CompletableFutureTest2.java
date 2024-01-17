package tech.xixing.threads.future;

import java.util.concurrent.CompletableFuture;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/4/12 11:03 上午
 */
public class CompletableFutureTest2 {
    public static void main(String[] args) {

        CompletableFuture<String> completableFuture=CompletableFuture.supplyAsync(CompletableFutureTest2::queryCode);
    }

    public static String queryCode(){
        try{
            Thread.sleep(100);
        }catch (Exception ignored){

        }
        return "123456";
    }

    public static Double fetchPrice(){
        try{
            Thread.sleep(100);
        }catch (Exception ignored){

        }
        return Math.random()*1000;
    }
}
