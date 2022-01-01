package com.test.java17;

import java.time.Period;

public class NullPointerExceptionTest {

    public static void main(String[] args) {
        System.out.println(toInt(1.00));
        System.out.println(toInt(null));
    }

    public static int toInt(Double d1) {
        return d1.intValue();
    }
}
