package com.zcx.blog.mapper;

import com.zcx.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> listRecentComment(@Param(value = "limit") Integer limit);
}
