package com.zcx.blog.mapper;

import com.zcx.blog.entity.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LinkMapper {
    List<Link> listLink(@Param(value = "status") Integer status);
}
