package com.test.java17;

/**
 * 封印类
 */
public class SealedTest {
    //在接口或者抽象类前边添加 sealed 关键字的，并且需要添加了一个允许扩展这个类或实现这个接口的类的清单。
    sealed interface Pets permits Cat,Dog {
        void say();
    }
    //Cat类必须实现Pets接口，且必须为final
     static final class Cat implements Pets {
        @Override
        public void say() {
            System.out.println("喵喵喵");
        }
    }
    static final class Dog implements Pets {
        @Override
        public void say() {
            System.out.println("汪汪汪");
        }
    }
    //编译报错 Pig is not allowed in the sealed hierarchy。不能实现被sealed修饰且未指定的类
    /*static final class Pig implements Pets{
        @Override
        public void say() {
            System.out.println("哼哼哼");
        }
    }*/
    public static void main(String[] args) {
        Pets pets = new Cat();
        pets.say();
    }
}
