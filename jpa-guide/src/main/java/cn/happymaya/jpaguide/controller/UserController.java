package cn.happymaya.jpaguide.controller;

import cn.happymaya.jpaguide.dao.UserRepository;
import cn.happymaya.jpaguide.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 保存用户
     * @param user 用户对象
     * @return 返回用户对象
     */
    @PostMapping(path = "user", consumes = {MediaType.APPLICATION_ATOM_XML_VALUE})
    public User addNewUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * 根据分页信息查询用户
     * @param request 请求数据
     * @return 返回用户信息
     */
    @GetMapping(path = "users")
    @ResponseBody
    public Page<User> getAllUsers(Pageable request) {
        return userRepository.findAll(request);
    }

}
