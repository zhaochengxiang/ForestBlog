package com.zcx.blog.mapper;

import com.zcx.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 添加
     *
     * @param category 分类
     * @return 影响行数
     */
    int insert(Category category);


    /**
     * 更新
     *
     * @param category 分类
     * @return 影响行数
     */
    int update(Category category);


    /**
     * 删除分类
     *
     * @param id 文章ID
     */
    int deleteCategory(Integer id);

    /**
     * 获得分类列表
     *
     * @return 列表
     */
    List<Category> listCategory();

}
