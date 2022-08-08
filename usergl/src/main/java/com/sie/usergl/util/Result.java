package com.sie.usergl.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 帮助我们处理响应数据
 */
@Data
public class Result implements Serializable {
    /**
     * 标记是否成功
     */
    private  boolean flag;
    /**
     * 要响应的数据
     */
    private  Object data;

    /**
     *
     */
    private String message;

    public Result() {
    }

    public Result(boolean flag, Object data, String message) {
        this.flag = flag;
        this.data = data;
        this.message = message;
    }

    public Result(Object data, String message) {
        this.message=message;
        this.data=data;
    }

    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public static Result success(String message, Object data){
        return  new Result(true,data,message);
    }
    public static Result fail(String message){
        return  new Result(false,message);
    }

}
