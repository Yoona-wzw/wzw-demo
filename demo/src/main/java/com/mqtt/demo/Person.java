package com.mqtt.demo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Person
 * @Description TODO
 * @Author wzw
 * @Date 2020/4/22 10:56
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class Person {
    private String name;
    private int age;

    public List<Person> getPersonList(){
        List<Person> list=new ArrayList<>();
        list.add(new Person().setName("liu").setAge(22));
        list.add(new Person().setName("zhang").setAge(20));
        list.add(new Person().setName("wang").setAge(12));
        return list;
    }
}
