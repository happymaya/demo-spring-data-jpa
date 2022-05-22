Spring Data Rest 和 Spring Data JPA 的关系
大概有如下几点。

Spring Data JPA 基于 JPA 协议提供了一套标准的 Repository 的操作统一接口，方法名和 @Query 都是有固定语法和约定的规则的。

Spring Data Rest 利用 JPA 的约定和语法，利用 Java 反射、动态代理等机制，很容易可以生成一套标准的 rest 风格的 API 协议操作。

也就是说 JPA 制定协议和标准，Spring Data Rest 基于这套协议生成 rest 风格的 Controller。