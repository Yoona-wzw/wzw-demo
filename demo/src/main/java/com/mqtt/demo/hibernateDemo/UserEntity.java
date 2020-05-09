package com.mqtt.demo.hibernateDemo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName UserEntity
 * @Description TODO
 * @Author wzw
 * @Date 2020/5/7 11:58
 * @Version 1.0
 **/
@Entity
@Table
public class UserEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
