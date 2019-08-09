package com.zcx.blog.controller;

import com.zcx.blog.entity.Result;
import com.zcx.blog.entity.Session;
import com.zcx.blog.entity.User;
import com.zcx.blog.service.SessionService;
import com.zcx.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public String login(String username,String password) {

        User user = userService.getUserByNameOrEmail(username);
        if(user==null) {
            return Result.of(404,"用户名无效！");
        } else if(!user.getUserPass().equals(password)) {
            return Result.of(404,"密码错误！");
        }

        user.setUserLastLoginTime(new Date());
        userService.updateUser(user);

        userService.saveSession(user, 0);

        Session session = sessionService.get();

        Map map = new HashMap();
        map.put("token", session.getId());

        return Result.of(200,map);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout() {
        sessionService.delete();
        return Result.of(200);
    }
}
