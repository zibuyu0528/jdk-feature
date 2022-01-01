package com.test.java17;

public class TextBlockTest {
    public static void main(String[] args) {
        //支持文本块之前
        String jsonBefore = "{\n" +
                "                  \"id\":1,\n" +
                "                  \"name\":张三,\n" +
                "                  \"age\"：20\n" +
                "                  } ";
        System.out.println(jsonBefore);
        //文本块
        String json = """
                  {
                  "id":1,
                  "name":张三,
                  "age"：20
                  }             
                """;
        System.out.println(json);

    }
}
