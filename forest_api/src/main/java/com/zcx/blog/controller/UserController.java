package com.zcx.blog.controller;

import com.zcx.blog.entity.Result;
import com.zcx.blog.entity.User;
import com.zcx.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody HashMap<String,String> map) {

        String username = map.get("username");
        String password = map.get("password");

        User user = userService.getUserByNameOrEmail(username);
        if(user==null) {
            return Result.of(404,"用户名无效！");
        } else if(!user.getUserPass().equals(password)) {
            return Result.of(404,"密码错误！");
        }

        user.setUserLastLoginTime(new Date());
        userService.updateUser(user);
        return Result.of(200);
    }
}
