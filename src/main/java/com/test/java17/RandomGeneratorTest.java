package com.test.java17;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class RandomGeneratorTest {
    public static void main(String[] args) {
        RandomGeneratorFactory.all().forEach(r -> {
            //输出全部实现
            System.out.println(r.name());
            //L32X64MixRandom
            //L128X128MixRandom
            //L64X128MixRandom
            //SecureRandom
            //L128X1024MixRandom
            //L64X128StarStarRandom
            //Xoshiro256PlusPlus
            //L64X256MixRandom
            //Random
            //Xoroshiro128PlusPlus
            //L128X256MixRandom
            //SplittableRandom
            //L64X1024MixRandom
        });
        RandomGeneratorFactory<RandomGenerator> l128X256MixRandom = RandomGeneratorFactory.of("L128X256MixRandom");
        // 使用时间戳作为随机数种子
        RandomGenerator randomGenerator = l128X256MixRandom.create(System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            System.out.println(randomGenerator.nextInt(10));
        }
    }
}
