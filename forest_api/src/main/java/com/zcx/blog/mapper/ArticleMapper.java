package com.zcx.blog.mapper;

import com.zcx.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {

    /**
     * 获得最新文章
     *
     * @param limit 查询数量
     * @return 列表
     */
    List<Article> listArticleByLimit(Integer limit);

    /**
     * 获得所有的文章
     *
     * @param criteria 查询条件
     * @return 文章列表
     */
    List<Article> findAll(HashMap<String, Object> criteria);

    /**
     * 添加文章
     *
     * @param article 文章
     * @return 文章
     */
    Integer insert(Article article);

    /**
     * 更新文章
     *
     * @param article 文章
     * @return 影响行数
     */
    Integer update(Article article);

    /**
     * 根据ID删除
     *
     * @param articleId 文章ID
     * @return 影响函数
     */
    Integer deleteById(Integer articleId);

}
