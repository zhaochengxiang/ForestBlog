package com.zcx.blog.entity;

import lombok.Data;

@Data
public class Session {
    /**
     * 当前会话id
     */
    private String id;

    /**
     * 当前登录验证码
     */
    private String captchaText;

    /**
     * 登录用户id
     */
    private int uid;

    /**
     * 类型，1是30天过期，0是不过期
     */
    private int expire;

    /**
     * 登录用户名称
     */
    private String username;

}
