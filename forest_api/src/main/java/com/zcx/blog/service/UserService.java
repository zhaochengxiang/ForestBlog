package com.zcx.blog.service;

import com.zcx.blog.entity.User;
import com.zcx.blog.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    public User getUserByNameOrEmail(String str) {
        return userMapper.getUserByNameOrEmail(str);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

}
