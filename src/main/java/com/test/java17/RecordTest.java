package com.test.java17;

public class RecordTest {
    record Book(String name, String author) {
        //可以有多个构造方法
        public Book() {
            this("", "");
        }
        //可以有非静态方法
        public String repeatName() {
            return name.repeat(2);
        }
        //可以有静态方法
        public static String desc(Book book) {
            return book.author() + "作者是" + book.name();
        }

    }

    public static void main(String[] args) {
        Book book = new Book("《三体》", "刘慈欣");
        System.out.println(book.name() + "：" + book.author());
        System.out.println(book.name + "：" + book.author);
        System.out.println(book.repeatName());
        System.out.println(Book.desc(book));
        Book bookDefault = new Book();
        System.out.println(bookDefault.name() + "：" + bookDefault.author());


    }

}
