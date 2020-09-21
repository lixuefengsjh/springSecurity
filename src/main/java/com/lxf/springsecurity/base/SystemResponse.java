package com.lxf.springsecurity.base;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author: lxf
 * @create: 2020-09-10 14:14
 * @description: 系统响应格式
 */
@Getter
public class SystemResponse<T> implements Serializable {
    private int responseCode;

    private String msg;

    private T date;


    public SystemResponse setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return  this;
    }



    public SystemResponse setMsg(String msg) {
        this.msg = msg;
        return  this;
    }



    public SystemResponse setDate(T date) {
        this.date = date;
        return  this;
    }

    public  static <T> SystemResponse  ok(T date){
        SystemResponse  systemResponse=new SystemResponse(200,"执行成功",date);
        return  systemResponse;
    }

    public static SystemResponse failed(){
        SystemResponse  systemResponse=new SystemResponse(500,"执行失败",null);
        return systemResponse;
    }
    public static  <T>  SystemResponse failed(T date){
        SystemResponse  systemResponse=new SystemResponse(500,"执行失败",date);
        return systemResponse;
    }
    public SystemResponse(int responseCode, String msg, T date) {

        this.responseCode = responseCode;
        this.msg = msg;
        this.date = date;
    }

    public SystemResponse() {
    }
}
