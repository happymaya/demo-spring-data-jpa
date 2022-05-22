# @Query

新增一个 @Query 的方法，快速体验一下 @Query 的使用方法，如下所示：
```java
package com.example.jpa.example1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDtoRepository extends JpaRepository<User,Long> {

   //通过query注解根据name查询user信息
   @Query("From User where name=:name")
   User findByQuery(@Param("name") String nameParam);
}
```

然后，新增一个测试类：
```java
package com.example.jpa.example1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryQueryTest {

   @Autowired
   private UserDtoRepository userDtoRepository;

   @Test
   public void testQueryAnnotation() {
       
       // 新增一条数据方便测试
       // userDtoRepository.save(User.builder().name("jackxx").email("123456@126.com").sex("man").address("shanghai").build());
       
       //调用上面的方法查看结果
       User user2 = userDtoRepository.findByQuery("jack");
       System.out.println(user2);
   }
}
```

运行的结果如下：
```bash
Hibernate: insert into user (address, email, name, sex, version, id) values (?, ?, ?, ?, ?, ?)

Hibernate: select user0_.id as id1_0_, user0_.address as address2_0_, user0_.email as email3_0_, user0_.name as name4_0_, user0_.sex as sex5_0_, user0_.version as version6_0_ from user user0_ where user0_.name=?

User(id=1, name=jack, email=123456@126.com, version=0, sex=man, address=shanghai)

```

通过上面的例们发现，这次不是通过方法名来生成查询语法，而是 @Query 注解在其中起了作用，使 `From User where name=:name"JPQL` 生效了。