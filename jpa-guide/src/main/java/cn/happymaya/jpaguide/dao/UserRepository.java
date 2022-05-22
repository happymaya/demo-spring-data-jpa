package cn.happymaya.jpaguide.dao;

import cn.happymaya.jpaguide.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<User, Long> {

    // 根据名称进行查询用户列表
    @Query
    List<User> findByName(String name);

    // 根据用户的邮箱和名称查询
    List<User> findByEmailAndName(String email, String name);
}
