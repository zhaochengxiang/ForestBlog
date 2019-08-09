package com.zcx.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcx.blog.entity.*;
import com.zcx.blog.enums.ArticleCommentStatus;
import com.zcx.blog.mapper.ArticleCategoryRefMapper;
import com.zcx.blog.mapper.ArticleMapper;
import com.zcx.blog.mapper.ArticleTagRefMapper;
import com.zcx.blog.util.HtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ArticleService {

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Autowired(required = false)
    private ArticleCategoryRefMapper articleCategoryRefMapper;

    @Autowired(required = false)
    private ArticleTagRefMapper articleTagRefMapper;

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

    public Article getArticleByStatusAndId(Integer status,Integer id) {
        Article article = articleMapper.getArticleByStatusAndId(status,id);

        List<Category> categoryList =
                articleCategoryRefMapper.listCategoryByArticleId(article.getArticleId());
        if (categoryList == null) {
            categoryList = new ArrayList<>();
        }
        article.setCategoryList(categoryList);

        List<Tag> tagList = articleTagRefMapper.listTagByArticleId(article.getArticleId());
        if (tagList == null) {
            tagList = new ArrayList<>();
        }
        article.setTagList(tagList);

        return article;
    }

    public void insertArticle(Article article)  {
        //添加文章
        article.setArticleCreateTime(new Date());
        article.setArticleUpdateTime(new Date());
        article.setArticleIsComment(ArticleCommentStatus.ALLOW.getValue());
        article.setArticleViewCount(0);
        article.setArticleLikeCount(0);
        article.setArticleCommentCount(0);
        if (article.getArticleOrder() == null) {
            article.setArticleOrder(1);
        }
        if (article.getArticleContent() !=null) {
            String summaryText = HtmlUtils.cleanHtmlTag(article.getArticleContent());
            article.setArticleSummary(summaryText);
        }

        articleMapper.insert(article);

        for (int i = 0; i < article.getCategoryList().size(); i++) {
            ArticleCategoryRef articleCategoryRef = new ArticleCategoryRef(article.getArticleId(),
                    article.getCategoryList().get(i).getCategoryId());
            articleCategoryRefMapper.insert(articleCategoryRef);
        }

        for (int i = 0; i < article.getTagList().size(); i++) {
            ArticleTagRef articleTagRef = new ArticleTagRef(article.getArticleId(),
                    article.getTagList().get(i).getTagId());
            articleTagRefMapper.insert(articleTagRef);
        }
    }

    public void updateArticle(Article article)  {
        article.setArticleUpdateTime(new Date());
        if (article.getArticleContent() !=null) {
            String summaryText = HtmlUtils.cleanHtmlTag(article.getArticleContent());
            article.setArticleSummary(summaryText);
        } else {
            article.setArticleSummary("");
        }
        articleMapper.update(article);

        if (article.getCategoryList() != null) {
            articleCategoryRefMapper.deleteByArticleId(article.getArticleId());

            for (int i = 0; i < article.getCategoryList().size(); i++) {
                ArticleCategoryRef articleCategoryRef = new ArticleCategoryRef(article.getArticleId(),
                        article.getCategoryList().get(i).getCategoryId());
                articleCategoryRefMapper.insert(articleCategoryRef);
            }
        }

        if (article.getTagList() != null) {
            articleTagRefMapper.deleteByArticleId(article.getArticleId());

            for (int i = 0; i < article.getTagList().size(); i++) {
                ArticleTagRef articleTagRef = new ArticleTagRef(article.getArticleId(),
                        article.getTagList().get(i).getTagId());
                articleTagRefMapper.insert(articleTagRef);
            }
        }
    }

    public void deleteArticle(Integer id)  {
        articleMapper.deleteById(id);
    }
}
