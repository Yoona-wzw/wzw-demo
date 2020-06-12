package com.wzw.wj.service;

import com.wzw.wj.dao.UserDao;
import com.wzw.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @ClassName UserService
 * @Description
 * @Author wzw
 * @Date 2020/5/22
 * @Version 1.0
 **/
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findUserByUsername(String username){
        User userRes = userDao.findByUsername(username);
        return userRes;
    }
    public boolean isExist(String username){
        User byUsername = userDao.findByUsername(username);
        if(byUsername==null){
            return false;
        }else{
            return true;
        }
    }
    public void addUser(User user){
        User res = userDao.save(user);
    }
}
