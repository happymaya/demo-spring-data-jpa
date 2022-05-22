# @Entity 里面的 JPA 注解，在 Java 多态场景下如何使用 ？

常会提到“实体类”（即我们前面的 User 类），它是对应数据库中表的 Metadata 映射。


## JPA 协议中关于 Entity 的相关规定

![JPA 协议](https://download.oracle.com/otn-pub/jcp/persistence-2_2-mrel-spec/JavaPersistence.pdf) 关于实体的规定：
1. 实体是直接进行数据库持久化操作的领域对象（即一个简单的 POJO，可以按照业务领域划分），必须通过 @Entity 注解进行标示；
2. 实体必须有一个 public 或者 protected 的无参数构造方法；
3. 持久化映射的注解可以标示在 Entity 的字段 field 上，如下所示：


除此之外，也可以将持久化注解运用在 Entity 里面的 get/set 方法上，通常是放在 get 方法中，如下所示：


概括起来，就是 Entity 里面的注解生效只有两种方式：将注解写在字段上或者将注解写在方法上（JPA 里面称 Property）。

但是**需要注意的是，在同一个 Entity 里面只能有一种方式生效**，也就是说，注解要么全部写在 field 上面，要么就全部写在 Property 上面，

因为我经常会看到有的同事分别在两种方式中加了注解后说：“哎呀，我的注解怎么没有生效呀！”因此这一点需要特别注意。
1. 只要是在 @Entity 的实体里面被注解标注的字段，都会被映射到数据库中，除了使用 @Transient 注解的字段之外；
2. 实体里面必须要有一个主键，主键标示的字段可以是单个字段，也可以是复合主键字段。

## 详细的注解都有哪些？

在 jakarta.persistence-api 的包路径下面大概有一百多个注解，最常见的，包括：
- @Entity
- @Table
- @Access
- @Id
- @GeneratedValue
- @Enumerated
- @Basic
- @Column
- @Transient
- @Lob
- @Temporal 等

### @Entity 

用于定义对象将会成为被 JPA 管理的实体，必填，将字段映射到指定的数据库表中，使用起来很简单，直接用在实体类上面即可，通过源码表达的语法如下：

### @Table 
用于指定数据库的表名，表示此实体对应的数据库里面的表名，非必填，默认表名和 entity 名字一样。

### @Access 

用于指定 entity 里面的注解是写在字段上面，还是 get/set 方法上面生效，非必填。在默认不填写的情况下，当实体里面的第一个注解出现在字段上或者 get/set 方法上面，就以第一次出现的方式为准；也就是说，一个实体里面的注解既有用在 field 上面，又有用在 properties 上面的时候，看下面的代码你就会明白。


那么由于 @Id 是实体里面第一个出现的注解，并且作用在字段上面，所以所有写在 get/set 方法上面的注解就会失效。而 @Access 可以干预默认值，指定是在 fileds 上面生效还是在 properties 上面生效。我们通过源码看下语法：


### @Id

定义属性为数据库的主键，一个实体里面必须有一个主键，但不一定是这个注解，可以和 @GeneratedValue 配合使用或成对出现。

### @GeneratedValue 主键生成策略，如下所示：其中，GenerationType 一共有以下四个值：

### @Enumerated 

这个注解很好用，因为它对 enum 提供了下标和 name 两种方式，用法直接映射在 enum 枚举类型的字段上。请看下面源码。

再来看一个 User 里面关于性别枚举的例子，你就会知道 @Enumerated 在这里没什么作用了，如下所示：

这时候插入两条数据，数据库里面的值会变成 MAIL/FMAIL，而不是“男性” / 女性。

经验分享： 如果我们用 @Enumerated（EnumType.ORDINAL），这时候数据库里面的值是 0、1。但是实际工作中，不建议用数字下标，因为枚举里面的属性值是会不断新增的，如果新增一个，位置变化了就惨了。并且 0、1、2 这种下标在数据库里面看着非常痛苦，时间长了就会一点也看不懂了。


### @Basic

表示属性是到数据库表的字段的映射。如果实体的字段上没有任何注解，默认即为 @Basic。也就是说默认所有的字段肯定是和数据库进行映射的，并且默认为 Eager 类型。

### @Transient
表示该属性并非一个到数据库表的字段的映射，表示非持久化属性。JPA 映射数据库的时候忽略它，与 @Basic 有相反的作用。也就是每个字段上面 @Transient 和 @Basic 必须二选一，而什么都不指定的话，默认是 @Basic。

### @Column

定义该属性对应数据库中的列名。

### @Temporal

用来设置 Date 类型的属性映射到对应精度的字段，存在以下三种情况：
- @Temporal(TemporalType.DATE)映射为日期 // date （只有日期）
- @Temporal(TemporalType.TIME)映射为日期 // time （只有时间）
- @Temporal(TemporalType.TIMESTAMP)映射为日期 // date time （日期+时间）
  
这里面的很多注解都可以省略，直接使用默认的就可以。如 @Basic、@Column 名字有一定的映射策略，所以可以省略。

此外，@Access 也可以省略，只要在这些类里面保持一致就可以了。

## 生成这些注解的小技巧

有时候老的 Table 非常多，我们一个一个去写 entity 会特别累，因此可以利用 IEDA 工具直接帮生成 Entity 类。关键步骤如下：
1. 打开 Persistence 视图，点击 Generate Persistence Mapping>，接着点击选中数据源；
2. 选择表和字段，并点击 OK.

这样就可以生成想要的实体了，多简单。如果是新库、新表，也可以先定义好实体，通过实体配置 JPA 的`spring.jpa.generate-ddl=true`，反向直接生成 DDL 操作数据库生成表结构。

但是需要注意的是，在生产环境中要把外间关联关系关闭，不然会出现意想不到的 ERROR，毕竟生产环境不同开发环境，可以通过在开发环境生成的表导出 DDL 到生产执行。我经常会利用生成 DDL 来做测试和写案例， 这样省去了创建表的时间，只需要关注我的代码就行了。

### 联合主键
在实际的工作中，我们会经常遇到联合主键的情况。所以在这里我们详细讲解一下，可以通过 javax.persistence.EmbeddedId 和 javax.persistence.IdClass 两个注解实现联合主键的效果。

### 通过 @IdClass 做到联合主键