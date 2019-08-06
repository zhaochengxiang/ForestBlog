package com.zcx.blog.mapper;

import com.zcx.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int update(User user);

    User getUserByNameOrEmail(String str) ;
}
