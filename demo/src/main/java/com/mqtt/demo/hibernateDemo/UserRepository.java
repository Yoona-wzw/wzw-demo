package com.mqtt.demo.hibernateDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName UserRepository
 * @Description TODO User表操作接口
 * @Author wzw
 * @Date 2020/5/7 12:14
 * @Version 1.0
 **/
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    /**
     * 根据用户名查询
     * @Param [username]
     * @return
    **/
    @Query("select t from UserEntity t where t.username=:username")
    UserEntity findByUserName(@Param("username") String username);
    /*
     * 查询全部
     * @Param []
     * @return
    **/
    @Query("select t from UserEntity t")//使用Jpql查询
    List<UserEntity> find();
    //使用sql查询 要设置nativeQuery=true
    @Query(value = "select * from user_entity",nativeQuery = true)
    List<UserEntity> findUsers();
    /**
     * 删除 必须加入@Modifying
     * @Param
     * @return
    **/
    @Modifying
    @Transactional
    @Query("delete from UserEntity u where u.id=:id")
     int deleteUserById(@Param("id") Integer id);

    @Query(value="insert into  user_entity (id,username,password) values(?,?,?)",nativeQuery = true)
    @Transactional
    @Modifying
     int insertUser(@Param("id") Integer id,@Param("username") String username,@Param("password") String password);

}