package com.wzw.wj.commons;

/**
 * @ClassName ResultFactory
 * @Description
 * @Author wzw
 * @Date 2020/5/27
 * @Version 1.0
 **/
public class ResultFactory {
    public static Result buildSuccessResult(String message){
        Result result=new Result();
        result.setMessage(message);
        return result;
    }
    public static Result buildErrorResult(String message){
        Result result=new Result();
        result.setMessage(message);
        return result;
    }
}
