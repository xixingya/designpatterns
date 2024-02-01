package tech.xixing.jdk21.loom;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class LoomTest {
    
    public static void testStructConcurrent() throws InterruptedException, ExecutionException {
        var a = new AtomicInteger(0);
        var begin = System.currentTimeMillis();
// 创建一个ShutdownOnFailure策略的StructuredTaskScope
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            List<StructuredTaskScope.Subtask<Integer>> subtasks = new ArrayList<>();
            // 启动100万个执行sleep 1s的子任务
            for (int i = 0; i < 1_000_000; i++) {
                subtasks.add(scope.fork(() -> {
                    int x = a.addAndGet(1);
                    Thread.sleep(Duration.ofSeconds(1));
                    return x;
                }));
            }
            // 等待子任务执行完毕，任意子任务执行失败则抛出异常
            scope.join().throwIfFailed();
            // 打印结果
            for (var task : subtasks) {
                var i = task.get();
                if (i % 10000 == 0) {
                    System.out.print(i + " ");
                }
            }
        } finally {
            // 打印总耗时
            System.out.println("Exec finished.");
            System.out.printf("Exec time: %dms.%n", System.currentTimeMillis() - begin);
        }
    }

    public static void testStructConcurrentThrow() throws InterruptedException, ExecutionException, TimeoutException {
        var a = new AtomicInteger(0);
        var begin = System.currentTimeMillis();
// 创建一个ShutdownOnFailure策略的StructuredTaskScope
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            List<StructuredTaskScope.Subtask<Integer>> subtasks = new ArrayList<>();
            // 启动100万个执行sleep 1s的子任务
            for (int i = 0; i < 1_000_000; i++) {
                subtasks.add(scope.fork(() -> {
                    int x = a.addAndGet(1);
                    if (x == 10) {
                        Thread.sleep(Duration.ofMillis(100));
                        throw new RuntimeException();
                    }
                    Thread.sleep(Duration.ofSeconds(1));
                    return x;
                }));
            }
            // 等待子任务执行完毕，任意子任务执行失败则抛出异常
            scope.join().throwIfFailed();

            // 等待子任务执行完毕，任意子任务执行失败则抛出异常
            scope.joinUntil(Instant.ofEpochMilli(100)).throwIfFailed();
            // 打印结果
            for (var task : subtasks) {
                var i = task.get();
                if (i % 10000 == 0) {
                    System.out.print(i + " ");
                }
            }
        } finally {
            // 打印总耗时
            System.out.println("Exec finished.");
            System.out.printf("Exec time: %dms.%n count:%d", System.currentTimeMillis() - begin,a.get());
        }
    }

    public static void test01() {
        var a = new AtomicInteger(0);
// 创建一个固定200个线程的线程池
        try (var vs = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> futures = new ArrayList<>();
            var begin = System.currentTimeMillis();
            // 向线程池提交1000个sleep 1s的任务
            for (int i = 0; i < 1_000; i++) {
                var future = vs.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return a.addAndGet(1);
                });
                futures.add(future);
            }
            // 获取这1000个任务的结果
            for (var future : futures) {
                var i = future.get();
                if (i % 100 == 0) {
                    System.out.print(i + " ");
                }
            }
            // 打印总耗时
            System.out.println("Exec finished.");
            System.out.printf("Exec time: %dms.%n", System.currentTimeMillis() - begin);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        test01();
        testStructConcurrent();
        testStructConcurrentThrow();
    }
}
