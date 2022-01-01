package com.test.java11;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Java11 文件操作测试
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        // 创建临时文件
        Path path = Files.writeString(Files.createTempFile("test", ".txt"), "Java从入门到放弃");
        System.out.println(path);
        //读取临时文件
        String s = Files.readString(path);
        System.out.println(s);
        //输出
        // Java从入门到放弃

    }
}
