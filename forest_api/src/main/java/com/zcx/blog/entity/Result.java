package com.zcx.blog.entity;

import com.zcx.blog.util.GsonUtils;

/**
 * Created by wayne on 15/11/2016.
 */
public class Result {
    private int code;
    private String message;
    private Object data;

    public static String of(int code) {
        Result result = new Result();
        result.setCode(code);
        return result.toJson();
    }

    public static String of(int code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result.toJson();
    }

    public static String of(int code, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setData(data);
        return result.toJson();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJson() {
        return GsonUtils.toJson(this);
    }
}
