package com.test.java11;

import java.util.stream.Stream;

/**
 * java 11新增字符串API
 */
public class StringFeatureTest {

    public static void main(String[] args) {
        String s1 = "   ";
        boolean blank = s1.isBlank();
        System.out.println(blank);
        String s2 = "第一行\n第二行\n第三行";
        Stream<String> lines = s2.lines();
        lines.forEach(System.out::println);
        String s3 = " aaacccddd ";
        //去除前后各种空白字符（包括全角半角）
        System.out.println("==="+s3.strip()+"===");
        //去除前面各种空白字符（包括全角半角）
        System.out.println("==="+s3.stripLeading()+"===");
        //去除后面各种空白字符（包括全角半角）
        System.out.println("==="+s3.stripTrailing()+"===");
        String s4 = "你好！";
        String repeat = s4.repeat(4);
        System.out.println(repeat);

    }
}
