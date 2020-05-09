package com.mqtt.demo.hibernateDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author wzw
 * @Date 2020/5/7 14:03
 * @Version 1.0
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/user")
    public UserEntity Oneuser(@RequestParam("name") String name){
        UserEntity user = userService.findUserByName(name);
        return user;
    }
    @GetMapping("/userAll")
    public List<UserEntity> userAll(){
       // List<UserEntity> all = userRepository.findAll();
       // List<UserEntity> all = userRepository.find();
       // List<UserEntity> users = userRepository.findUsers();
        List list = userService.find();
        return list;
    }
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Integer id){
        userRepository.deleteUserById(id);
        System.out.println("ggs");
    }
    @PostMapping("/user")
    public void saveUser(@RequestBody UserEntity userEntity){
        userRepository.insertUser(userEntity.getId(),userEntity.getUsername(),userEntity.getPassword());
    }
    @PostMapping("/user2")
    public void saveUser2(@RequestBody UserEntity userEntity){
        userService.saveUser(userEntity);
    }
}
