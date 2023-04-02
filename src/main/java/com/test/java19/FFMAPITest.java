package com.test.java19;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Copy;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.util.Arrays;

import static java.lang.foreign.SegmentAllocator.implicitAllocator;
import static java.lang.foreign.ValueLayout.*;

public class FFMAPITest {
    public static void main(String[] args) throws Throwable {
        // 1. 在C库路径上查找外部函数
        SymbolLookup stdlib = Linker.nativeLinker().defaultLookup();

        // 2. 获取 C 标准库中“strlen”函数的句柄
        MethodHandle strlen = Linker.nativeLinker().downcallHandle(
                stdlib.lookup("strlen").orElseThrow(),
                FunctionDescriptor.of(JAVA_LONG, ADDRESS));

        // 3. 将 Java 字符串转换为 C 字符串并存储在堆外内存中
        MemorySegment str = implicitAllocator().allocateUtf8String("hello Java19!");

        // 4. 调用外部函数
        long len = (long) strlen.invoke(str);

        System.out.println("len = " + len);
    }
}
