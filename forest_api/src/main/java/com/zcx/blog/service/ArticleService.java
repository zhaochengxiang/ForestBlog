package com.zcx.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcx.blog.entity.Article;
import com.zcx.blog.entity.Category;
import com.zcx.blog.mapper.ArticleCategoryRefMapper;
import com.zcx.blog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ArticleService {

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Autowired(required = false)
    private ArticleCategoryRefMapper articleCategoryRefMapper;

    public List<Article> listRecentArticle(Integer limit) {
        return articleMapper.listArticleByLimit(limit);
    }

    public PageInfo<Article> pageArticle(Integer pageIndex,
                         Integer pageSize,
                         HashMap<String, Object> criteria) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Article> articleList = articleMapper.findAll(criteria);

        for (int i = 0; i < articleList.size(); i++) {
            //封装CategoryList
            List<Category> categoryList =
                    articleCategoryRefMapper.listCategoryByArticleId(articleList.get(i).getArticleId());
            if (categoryList == null) {
                categoryList = new ArrayList<>();
            }
            articleList.get(i).setCategoryList(categoryList);
        }

        return new PageInfo<>(articleList);
    }

    public Article getArticleByStatusAndId(Integer status,Integer id) { return articleMapper.getArticleByStatusAndId(status,id); }

    public void insertArticle(Article article)  {
        articleMapper.update(article);
    }

    public void updateArticle(Article article)  { articleMapper.insert(article); }

    public void deleteArticle(Integer id)  {
        articleMapper.deleteById(id);
    }
}
