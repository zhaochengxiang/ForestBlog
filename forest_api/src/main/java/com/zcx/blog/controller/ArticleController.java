package com.zcx.blog.controller;

import com.github.pagehelper.PageInfo;
import com.zcx.blog.entity.Article;
import com.zcx.blog.entity.ArticleParam;
import com.zcx.blog.entity.Result;
import com.zcx.blog.enums.ArticleStatus;
import com.zcx.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/recent")
    @ResponseBody
    public String recentArticle() {
        List<Article> articleList = articleService.listRecentArticle(5);
        return Result.of(200, articleList);
    }

    @RequestMapping(value="/get")
    @ResponseBody
    public String get(Integer status, Integer id) {
        if (status == null) {
            status = ArticleStatus.PUBLISH.getValue();
        }

        Article article = articleService.getArticleByStatusAndId(status,id);
        return Result.of(200, article);
    }

    @RequestMapping(value = "/search")
    @ResponseBody
    public String search(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                         @RequestParam(required = false) String status,
                         @RequestParam(required = false) String keywords) {
        HashMap<String, Object> criteria = new HashMap<>(1);
        if (status != null) {
            criteria.put("status", status);
        }
        if (keywords != null) {
            criteria.put("keywords", keywords);
        }

        PageInfo<Article> articlePageInfo = articleService.pageArticle(pageIndex, pageSize, criteria);
        return Result.of(200, articlePageInfo);
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(ArticleParam articleParam) {

        Article article = new Article(articleParam);
        articleService.insertArticle(article);
        return Result.of(200);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(ArticleParam articleParam) {

        Article article = new Article(articleParam);
        articleService.updateArticle(article);
        return Result.of(200);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(Integer id) {
        articleService.deleteArticle(id);
        return Result.of(200);
    }
}
