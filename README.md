首先看下Java各个版本发布时间

| 版本            | 发布时间     |
| --------------- | ------------ |
| Java SE 8 (LTS) | 2014 年 3 月 |
| Java 9          | 2017 年 9 月 |
| Java 10         | 2018 年 3 月 |
| Java 11 (LTS)   | 2018 年 9 月 |
| Java 12         | 2019 年 3 月 |
| Java 13         | 2019 年 9 月 |
| Java 14         | 2020 年 3 月 |
| Java 15         | 2020 年 9 月 |
| Java 16         | 2021 年 3 月 |
| Java 17 (LTS)   | 2021 年 9 月 |

从Java9开始，为了更快地迭代，以及跟进社区反馈，Oracle的开始使用新的发行节奏，半年一个小版本，每三年一个大版本，并且承诺不会跳票，以免出现类似Java9那样多次延迟发布的尴尬局面。最近一个LTS版本是2021 年 9 月， 发布的 Java 17。

虽然目前大部分公司日常开发使用的还是Java8，不过还是需要多了解一下新版本的特性。首先在新的版本发布模式下，大多数变更首先需要经过“预览”阶段，也就是说它们被添加到一个版本中，但还没有完成。人们可以尝试使用它们，但不建议将其用在生产环境中。接下来代码示例中使用的特性都是已经正式添加到该版本，并且已经过了预览阶段。

####  Java11新特性简单使用

##### 1.String API

字符串可能是是日常开发中最常用的一个类，String类的方法使用频率也非常高。Java11新增了一系列实用的API

1. isBlank判空

   ```java
   String s = "   ";
   boolean blank = s.isBlank();
   //输出true
   System.out.println(blank);
   ```

2. lines()按行分割获取stream流

   ```java
   String s2 = "第一行\n第二行\n第三行";
   Stream<String> lines = s2.lines();
   lines.forEach(System.out::println);
   //输出
   //第一行
   //第二行
   //第三行
   ```

3. strip()去除空白字符。`trim` 只能去除半角空格，而 `strip` 是**去除各种空白符**

   ```java
   String s3 = " aaacccddd ";
   //去除前后各种空白字符（包括全角半角）
   System.out.println("==="+s3.strip()+"===");
   //去除前面各种空白字符（包括全角半角）
   System.out.println("==="+s3.stripLeading()+"===");
   //去除后面各种空白字符（包括全角半角）
   System.out.println("==="+s3.stripTrailing()+"===");
   //输出
   //===aaacccddd===
   //===aaacccddd ===
   //=== aaacccddd===
   ```

4. repeat()复制字符串

   ```java
   String s4 = "你好！";
   String repeat = s4.repeat(4);
   System.out.println(repeat);
   //输出
   //你好！你好！你好！你好！
   ```

##### 2. 文件操作API

java.nio.file.Files新增方法，读写文件更加简单

```java
//创建临时文件，写入内容
Path path = Files.writeString(Files.createTempFile("test", ".txt"), "Java从入门到放弃");
System.out.println(path);
//读取临时文件
String s = Files.readString(path);
System.out.println(s);
//输出
//Java从入门到放弃
```

##### 3. 更好用的Http Client API

从java9的jdk.incubator.httpclient模块迁移到java.net.http模块，包名由jdk.incubator.http改为java.net.http。这是一个流畅、易于使用的API，完全支持HTTP/1.1和HTTP/2，可以同步或者异步处理响应。可以不需要再依赖第三方Http Client

```java
HttpClient client = HttpClient.newBuilder().
                //connect timeout
                connectTimeout(Duration.ofSeconds(5))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.baidu.com"))
                //read timeout
                .timeout(Duration.ofSeconds(5))
                .build();
        // 异步
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

        // 同步
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
```

##### 4.其他部分特性

- 局部变量类型推断。Java 11 支持在lambda表达式参数中使用[局部变量语法](https://baeldung-cn.com/java-var-lambda-params)(var关键字)。我们可以利用此特性将修饰符应用于局部变量，例如定义类型注解(type annotation)：

  ```
  List<String> sampleList = Arrays.asList("Java", "Kotlin");
  String resultString = sampleList.stream()
    .map((@Nonnull var x) -> x.toUpperCase())
    .collect(Collectors.joining(", "));
  assertThat(resultString).isEqualTo("JAVA, KOTLIN");
  ```

- 直接运行 Java 文件。省略javac，直接通过命令执行.java文件

- 推出的一款新的低延迟垃圾回收器 —— ZGC

- 新的实验性垃圾回收器 —Epsilon。Epsilon 垃圾回收器不执行任何垃圾回收工作，适用于模拟内存不足错误的场景。有一些特定的用例可能会有用：

  - 性能测试
  - 内存压力测试
  - VM 接口测试
  - 存活极短的任务

  要启用它，添加 `-XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC` 参数。

- 飞行记录器（Flight Recorder）**[飞行记录器](https://baeldung-cn.com/java-flight-recorder-monitoring) (JFR) 之前是 Oracle JDK 中的一个商用产品，现已在Open JDK中开源**。JFR 是一个性能分析工具，我们可以使用它从正在运行的 Java 应用程序中收集诊断和分析数据。要开启一个时长为 120 秒的 JFR 记录，我们可以使用以下参数：

  ```
  -XX:StartFlightRecording=duration=120s,settings=profile,filename=java-demo-app.jfr
  ```

  JFR性能开销通常低于 1%，因此可以将它用于生产环境。记录的数据保存在.jfr文件中。为了分析和可视化数据，我们还需要使用另一款工具 —— [JDK Mission Control](https://baeldung-cn.com/java-flight-recorder-monitoring) (JMC) 。

- 还有一些重要的变化：

  - 实现了新的 ChaCha20 和 ChaCha20-Poly1305 加密算法取代不安全的RC4。
  - Support for cryptographic key agreement with Curve25519 and Curve448 replace the existing ECDH scheme
  - 升级TLS版本到1.3，提升了安全性和性能
  - 支持 Unicode 10， 带来了更多的字符、符号和表情符号

#### Java17新特性简单使用

注意，下边的新特性包含在12-17中预览并且发布的特性。

##### 1 文本快 

在支持文本块之前，相信都对Json等字符串转义、换行感到头疼，虽然大多数IDE支持复制后自动转义，不过大量的转义拼接导致可读性极差。

[文本块](https://openjdk.java.net/jeps/378)在 Java 13 中预览发布，并正式添加到 Java 15 中，它可以简化多行字符串的写法，支持换行，并在不需要转义字符的情况下保持缩进。

```java
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
        //要使用一个文本块，只需要这样，大大提高可读性
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

```

##### 2.增强Switch

 [switch表达式](https://openjdk.java.net/jeps/361)在新的版本中不断在增强功能，“万物皆可Switch“

```java
package com.test.java17;

public class SwitchCaseTest {
    public static void main(String[] args) {
//        Object obj = 1;
        //Object obj = "1";
//        Object obj = null;
        Object obj = 1L;
      	//跟我们熟悉的版本最明显的区别是没有了 break 语句。
        switch (obj) {
            case null -> {
                System.out.println("obj is null ");
                throw new NullPointerException();
            }
            case String s -> System.out.println("String: " + s);
            case Integer i -> System.out.println("Integer");
            default -> System.out.println("default");
        }
    }
}
```

相信绝大多数人都碰到过这种情况：忘记在 switch 里添加 break 语句，只有当代码在运行之后，并且发生错误之后才能发现。。。

switch 表达式通过一种有趣的方式修复了这个问题，只需要用逗号隔开同一个代码块里所有的值。不需要使用 break ！它会替你处理好！ 省略掉break，也让Java 语法更加简洁。

switch 表达式还新增了 yield 关键字。如果一个 case 进入了一个代码块，yield 将被作为 switch 表达式的返回语句。

```java
package com.test.java17;

public class SwitchCaseTest {
    public static void main(String[] args) {
        Object obj = 1;
        //Object obj = "1";
//        Object obj = null;
//        Object obj = 1L;
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
```

##### 3. 封印类

新增关键字sealed。sealed修饰的类和接口限制其他的类或者接口的扩展和实现。

```java
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
    static final class Pig implements Pets{
        @Override
        public void say() {
            System.out.println("哼哼哼");
        }
    }
    public static void main(String[] args) {
        Pets pets = new Cat();
        pets.say();
    }
}

```

##### 4. record 类

[record类](https://openjdk.java.net/jeps/395)在 Java 14 加入预览，并正式添加到 Java 16 中，替代传统的POJO 类。会自动实现equals()和 hashcode()方法会自动实现，toString()将返回这个类实例包含的所有字段的值。相对传统的POJO类的，让代码更加简洁。另外需要注意**record 类是 final 和不可变的，属性也是final的。意味着类实例一旦被创建，它的字段就不能被修改。可以在 record 类中声明方法，包括非静态方法和静态方法**

```java
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

```

##### 5. Instanceof模式匹配

[模式匹配](https://openjdk.java.net/jeps/394)是 Java 消除冗长语法的路上的另一个举措。模式匹配在 Java 14加入预览，并正式添加到 Java 16 中，它可以在 instanceof 满足后省掉不必要的类型转换。

```java
package com.test.java17;

public class InstanceOfTest {
    public static void main(String[] args) {
        travel(new Plane());
      	//
    }

    public static void travel(Object vehicle) {
        //以前的写法
        if (vehicle instanceof Plane) {
            ((Plane) vehicle).fly();
        } else if (vehicle instanceof Car) {
            ((Car) vehicle).drive();
        }
        //Java17的写法，看起来更加的简洁
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

```

##### 6. 优化的空指针异常可读性

优化空指针异常](https://openjdk.java.net/jeps/358)在 Java 14 中正式发布，提高了空指针异常(NullPointerException，简称 NPE)的可读性，可以打印出在抛出异常位置所调用的方法的名称和空变量的名称。例如，如果你调用 a.b.getName()，而 b 为空，那么异常的堆栈跟踪信息会告诉你调用 getName()失败，因为 b 是空的。

```java
public static void main(String[] args) {
   System.out.println(toInt(1.00));
   System.out.println(toInt(null));
}

public static int toInt(Double d1) {
  return d1.intValue();
}
//异常的堆栈跟踪信息会告诉你调用 intValue()失败，因为 d1 是空的。
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "java.lang.Double.intValue()" because "d1" is null
	at com.test.java17.NullPointerExceptionTest.toInt(NullPointerExceptionTest.java:13)
	at com.test.java17.NullPointerExceptionTest.main(NullPointerExceptionTest.java:9)
//11的异常堆栈跟踪信息
  Exception in thread "main" java.lang.NullPointerException
	at com.test.java11.NullPointerExceptionTest.toInt(NullPointerExceptionTest.java:11)
	at com.test.java11.NullPointerExceptionTest.main(NullPointerExceptionTest.java:7)
 
```

##### 7.增强的伪随机数生成器

增强的伪随机数生成器。将为伪随机数生成器 (PRNG) 提供新的接口类型和实现，包括可跳转的 PRNG 和额外的一类可拆分 PRNG 算法 (LXM)。新接口RandomGenerator将为所有现有的和新的 PRNG 提供统一的 API。将提供四个专门的 RandomGenerator 接口。推动该计划的重点是 Java 伪随机数生成领域的多个改进领域。这项工作不需要提供许多其他 PRNG 算法的实现。但是已经添加了一些常用算法，这些算法已经广泛部署在其他编程语言环境中。该计划的目标包括：

- 使在应用程序中交替使用各种 PRNG 算法变得更容易。
- 改进了对基于流的编程的支持，提供了 PRNG 对象流。
- 消除现有 PRNG 类中的代码重复。
- 保留类的现有行为java.util.Random。

```java
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
        //
        RandomGeneratorFactory<RandomGenerator> l128X256MixRandom = RandomGeneratorFactory.of("L128X256MixRandom");
        // 使用时间戳作为随机数种子
        RandomGenerator randomGenerator = l128X256MixRandom.create(System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            System.out.println(randomGenerator.nextInt(10));
        }
    }
}

```

##### 8.其它新特性

- 始终严格的浮点语义

  在 Java SE 1.2 之前，所有的浮点计算都是严格的，但是以当初的情况来看，过于严格的浮点计算在当初流行的 x86 架构和 x87 浮点协议处理器上运行，需要大量的额外的指令开销，所以在 Java SE 1.2 开始，需要手动使用关键字 **strictfp**（strict float point） ,它可以用在类、接口或者方法上，被 strictfp 修饰的部分中的 float 和 double 表达式会进行严格浮点计算。但是随着时代发展，硬件早已发生巨变，当初的问题已经不存在了，所以从 Java 17 开始，删除了以前的默认语义，现在严格执行所有浮点操作。关键字**strictfp**仍然存在，但它没有效果。

-  JEP 410: Remove the Experimental AOT and JIT Compiler

  [删除实验性 AOT 和 JIT 编译器](https://openjdk.java.net/jeps/410)，它们几乎没有使用，但需要大量维护工作。该计划要求维护 Java 级别的 JVM 编译器接口，以便开发人员可以继续使用外部构建的编译器版本进行 JIT 编译。AOT 编译（jaotc 工具）作为一个实验性特性被整合到[JDK 9](https://www.infoworld.com/article/3227244/java-9-is-here-everything-you-need-to-know.html)中。该工具使用[Graal 编译器](https://www.infoworld.com/article/3604476/graalvm-boosts-java-performance-with-truffle-framework.html)，它本身是用 Java 编写的，用于 AOT 编译。这些实验性功能未包含在[JDK 16 中](https://www.infoworld.com/article/3569150/jdk-16-the-new-features-in-java-16.html)由 Oracle 发布的版本，没有人抱怨。根据规定的计划，将删除三个 JDK 模块： jdk.aot（jaotc 工具）；internal.vm.compiler，Graal 编译器；和 jdk.internal.vm.compiler.management，Graal MBean。与 AOT 编译相关的 HotSpot 代码也将被删除。

- JEP 412: Foreign Function & Memory API (Incubator)

  加入一个新的API，允许Java程序安全有效地访问Java堆之外的外部内存。连续在14,15,16预览，17种发布

- 与平台无关的[矢量 API](https://openjdk.java.net/jeps/414)作为孵化 API集成到[JDK 16](https://www.infoworld.com/article/3569150/jdk-16-the-new-features-in-java-16.html)中，将在 JDK 17 中再次孵化，提供一种机制来表达矢量计算，这些计算在运行时可靠地编译为支持的 CPU [**架构**](https://www.jdon.com/tags/249)上的最佳矢量指令。这比等效的标量计算获得了更好的性能。在 JDK 17 中，向量 API 已针对性能和实现进行了增强，包括在字节向量与布尔数组之间进行转换的增强功能。

