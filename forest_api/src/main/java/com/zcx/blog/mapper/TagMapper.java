package com.zcx.blog.mapper;

import com.zcx.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {

    /**
     * 根据ID删除
     *
     * @param tagId 标签ID
     * @return 影响行数
     */
    int deleteById(Integer tagId);

    /**
     * 添加
     *
     * @param tag 标签
     * @return 影响行数
     */
    int insert(Tag tag);

    /**
     * 更新
     * @param tag 标签
     * @return 影响行数
     */
    int update(Tag tag);

    /**
     * 获得标签列表
     *
     * @return 列表
     */
    List<Tag> listTag() ;
}
