package com.wzw.wj.dao;

import com.wzw.wj.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Interface UserDao
 * @Description
 * @Author wzw
 * @Date 2020/5/22
 * @Version 1.0
 **/
@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    User findByUsername(String username);
    
}
