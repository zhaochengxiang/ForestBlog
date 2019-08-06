package com.zcx.blog.mapper;

import com.zcx.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    List<Tag> listTag() ;
}
