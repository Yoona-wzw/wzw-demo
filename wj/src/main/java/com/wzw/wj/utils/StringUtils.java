package com.wzw.wj.utils;

import java.util.Random;

/**
 * @ClassName StringUtils
 * @Description
 * @Author wzw
 * @Date 2020/5/26
 * @Version 1.0
 **/
public class StringUtils {
    public static String getRandomString(int length){
        String base="abcdefghijklmnopqrstuvwxyz0123456789";
        Random random=new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<length;i++){
            int number = random.nextInt(base.length());
            stringBuffer.append(base.charAt(number));
        }
        return stringBuffer.toString();
    }
}
