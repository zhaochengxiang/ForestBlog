package com.zcx.blog.mapper;


import com.zcx.blog.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<Notice> listNotice(@Param(value = "status") Integer status);
}
