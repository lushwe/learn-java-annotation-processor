# learn-java-annotation-processor

- 学习注解处理器的demo
- 使用javapoet代码生成框架生成class文件
- 具体使用方式：
    - 1、将本工程打成jar包
    - 2、新建工程，引入该jar包
    - 3、编写一个普通类，在类上加上 `@Hello` 注解
    - 4、直接编译
    - 5、在target目录下，和加上 `@Hello` 注解的普通类相同目录下，会有一个 `HelloWorld.class` 文件，该class文件就是在编译的时候注解处理器自动生成
    - 6、同时，在 `target/generated-sources/annotations` 目录下，会有一个 `HelloWorld.java` 文件
- 说明：上面的目录都是在使用 `maven` 的情况下才会有，至于 `gradle` 目录不太一样