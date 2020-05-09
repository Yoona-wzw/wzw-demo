package com.mqtt.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @ClassName LambdaDemo
 * @Description TODO
 * @Author wzw
 * @Date 2020/4/13 12:27
 * @Version 1.0
 **/
public class LambdaDemo {
    public static void main(String[] args) {
        //线程内部类实现
       new Thread(new Runnable() {
           @Override
           public void run() {
                   System.out.println("匿名内部类实现");
               }
       }).start();
       //lambda
        new Thread(()-> System.out.println("java8 lambda实现线程")).start();
        Person person1=new Person();
        List<Person> personList = person1.getPersonList();
        //personList.forEach(person-> System.out.println(person.toString()));

       // Consumer<Person> changeAge=e->e.setAge(e.getAge()+3);
       // personList.forEach(changeAge);
       // personList.forEach(System.out::println);//::表示方法引用，可以引用其他方法；

        //filter对集合进行过滤
      //  personList.stream().filter(e->e.getAge()>20).forEach(e-> System.out.println(e.toString()));
        //多重过滤 and or 调用多次filter
//        Predicate<Person> ageFilter =e -> e.getAge()<20;
//        Predicate<Person> nameFilter =e -> e.getName().equals("wang");
//        personList.stream().filter(ageFilter).filter(nameFilter).forEach(e -> System.out.println(e.toString()));
//        personList.stream().filter(ageFilter.and(nameFilter)).forEach(e -> System.out.println(e.toString()));
        //limit限制条件
//        personList.stream().limit(2).forEach(e -> System.out.println(e.toString()));
//        System.out.println("----------------");
//        personList.stream().limit(2).filter(e -> e.getAge()>20).forEach(e -> System.out.println(e.toString()));
//sorted排序
        //年龄排序
     //   personList.stream().sorted((p1,p2) -> (p1.getAge()-p2.getAge())).forEach(e -> System.out.println(e.toString()));
       //姓名排序
     //   personList.stream().sorted(Comparator.comparing(Person::getName)).forEach(e -> System.out.println(e.toString()));
    //max min 获取最大最小值
//        Person maxAge = personList.stream().max(Comparator.comparing(Person::getAge)).get();
//        System.out.println(maxAge.toString());
//        Person minAge = personList.stream().min(Comparator.comparing(Person::getAge)).get();
//        System.out.println(minAge.toString());
        //map 遍历每个元素
     //   personList.stream().map(e -> e.setAge(e.getAge()+3)).forEach(e -> System.out.println(e.toString()));
    //reduce：也是对所有值进行操作，但它是将所有值，按照传入的处理逻辑，将结果处理合并为一个
      //  List<Integer> integers = Arrays.asList(2, 4, 5, 8);
      //  Integer totalAge = integers.stream().reduce((s1, s2) -> s1 + s2).get();
      //  System.out.println(totalAge);
        int a=1;
        int i  = a<< 30;
        System.out.println(i);
    }
}
