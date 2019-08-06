package com.zcx.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcx.blog.entity.Article;
import com.zcx.blog.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class ArticleService {

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    public List<Article> listRecentArticle(Integer limit) {
        return articleMapper.listArticleByLimit(limit);
    }

    public PageInfo<Article> pageArticle(Integer pageIndex,
                         Integer pageSize,
                         HashMap<String, Object> criteria) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Article> articleList = articleMapper.findAll(criteria);

        return new PageInfo<>(articleList);
    }

    public void deleteArticle(Integer id)  {
        articleMapper.deleteById(id);
    }

    public void insertArticle(Article article)  {
        articleMapper.update(article);
    }

    public void updateArticle(Article article)  { articleMapper.insert(article); }
}
