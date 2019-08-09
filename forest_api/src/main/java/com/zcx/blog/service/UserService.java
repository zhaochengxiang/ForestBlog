package com.zcx.blog.service;

import com.zcx.blog.entity.Session;
import com.zcx.blog.entity.User;
import com.zcx.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private SessionService sessionService;

    public User getUserByNameOrEmail(String str) {
        return userMapper.getUserByNameOrEmail(str);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void saveSession(User user, int expire) {
        Session session = sessionService.get();
        session.setUid(user.getUserId());
        session.setUsername(user.getUserName());
        session.setExpire(expire);
        sessionService.save(session);
    }

}
