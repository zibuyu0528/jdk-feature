package com.test.java17;

public class InstanceOfTest {
    public static void main(String[] args) {
        travel(new Plane());
    }

    public static void travel(Object vehicle) {
        //以前的写法
        if (vehicle instanceof Plane) {
            ((Plane) vehicle).fly();
        } else if (vehicle instanceof Car) {
            ((Car) vehicle).drive();
        }
        //Java17的写法
        if (vehicle instanceof Plane plane) {
            plane.fly();
        } else if (vehicle instanceof Car car) {
            car.drive();
        }
    }

    static class Plane {
        public void fly() {
            System.out.println("坐飞机");
        }
    }

    static class Car {
        public void drive() {
            System.out.println("开车");
        }
    }
}
