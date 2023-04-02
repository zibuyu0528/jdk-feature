package com.test.java19;

import jdk.incubator.concurrent.StructuredTaskScope;
import java.util.concurrent.Future;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class StructuredTaskScopeTest {
    public static void main(String[] args) {
        //java17的随机数生成器
        RandomGeneratorFactory<RandomGenerator> l128X256MixRandom = RandomGeneratorFactory.of("L128X256MixRandom");
        RandomGenerator randomGenerator = l128X256MixRandom.create(System.currentTimeMillis());
        //结构化并发
        try (var scope = new StructuredTaskScope<>()) {
            // 使用fork方法派生线程来执行子任务
            Future<Integer> task1 = scope.fork(() -> {
                int i = randomGenerator.nextInt(10);
                Thread.sleep(i);
                System.out.println("task1 complete....");
                return i;
            });
            Future<Integer> task2 = scope.fork(() -> {
                int i = randomGenerator.nextInt(5);
                Thread.sleep(i);
                System.out.println("task2 complete....");
                return i;
            });
            // 等待线程完成
            scope.join();
            System.out.println("task complete....");
            System.out.println("task1 return " + task1.get());
            System.out.println("task2 return " + task2.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
