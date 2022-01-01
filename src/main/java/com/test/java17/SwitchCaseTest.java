package com.test.java17;

public class SwitchCaseTest {
    public static void main(String[] args) {
        Object obj = 1;
        //Object obj = "1";
//        Object obj = null;
//        Object obj = 1L;
        switch (obj) {
            case null -> {
                System.out.println("obj is null ");
            }
            case String s -> System.out.println("String: " + s);
            case Integer i -> System.out.println("Integer");
            default -> System.out.println("default");
        }

        Object result = switch (obj) {
            case null -> {
                yield null;
            }
            case String s -> s;
            case Integer i -> i.toString();
            default -> "default";
        };
        System.out.println(result);
    }
}

