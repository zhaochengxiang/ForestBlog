package com.zcx.blog.mapper;

import com.zcx.blog.entity.ArticleCategoryRef;
import com.zcx.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleCategoryRefMapper {

    /**
     * 添加文章和分类关联记录
     * @param record 关联对象
     * @return 影响行数
     */
    int insert(ArticleCategoryRef record);

    /**
     * 根据文章ID删除记录
     * @param articleId 文章ID
     * @return 影响行数
     */
    int deleteByArticleId(Integer articleId);

    /**
     * 根据文章ID获得分类列表
     *
     * @param articleId 文章ID
     * @return 分类列表
     */
    List<Category> listCategoryByArticleId(Integer articleId);
}
