package com.mqtt.demo;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @ClassName GuavaDemo
 * @Description TODO google提供的java 类库
 * @Author wzw
 * @Date 2020/4/30 9:14
 * @Version 1.0
 **/
public class GuavaDemo {
    public static void jionerList(){
        /*Joiner.on 把list用，连接
        * skipNulls跳过null值
        * */

        ArrayList<String> arry = Lists.newArrayList("1","2",null,"3","4");
       // String join = Joiner.on(",").join(arry);
        String join = Joiner.on(",").skipNulls().join(arry);
        String result = Joiner.on(",").useForNull("呵呵").join(arry);
      //  System.out.println(result);
        String test="12, 33 , 44 , fk , lk ";
        List<String> strings = Splitter.on(",").trimResults().splitToList(test);
       // System.out.println(strings);
        String str="123456677";
        List<String> strings1 = Splitter.fixedLength(3).splitToList(str);
        CharMatcher charMatcher = CharMatcher.is('7');
        System.out.println(charMatcher.removeFrom(str));
        //System.out.println(strings1);
        Maps.newHashMap();
        //Multimap 把相同的key值得value放在list中
        ArrayListMultimap<String, String> amulitmap = ArrayListMultimap.create();
        amulitmap.put("one","zs");
        amulitmap.put("one","ff");
        amulitmap.put("one","ss");
        amulitmap.put("four","lsi");
        amulitmap.put("five","wz");
        List<String> strings2 = amulitmap.get("one");//得到key值对应得value list
        Multiset<String> keys = amulitmap.keys();//返回key 的数量和value的数量一样
        Set<String> strings3 = amulitmap.keySet();//以set<>返回不重复的set列表
        System.out.println(strings2);
        System.out.println(keys);
        System.out.println(strings3);
    }

    public static void optionalTest(){
    }
    public static void main(String[] args) {
        optionalTest();
        jionerList();
    }
}
