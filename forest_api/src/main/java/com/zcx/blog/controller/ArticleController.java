package com.zcx.blog.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableMap;
import com.zcx.blog.entity.Article;
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
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/recent")
    @ResponseBody
    public String recentArticle() {
        List<Article> articleList = articleService.listRecentArticle(5);
        Map map = ImmutableMap.of("data", articleList);
        return Result.of(200, map);
    }

    @RequestMapping(value="/get")
    @ResponseBody
    public String get(Integer status, Integer id) {
        if (status == null) {
            status = ArticleStatus.PUBLISH.getValue();
        }

        Article article = articleService.getArticleByStatusAndId(status,id);
        Map map = ImmutableMap.of("data", article);
        return Result.of(200, map);
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
        Map map = ImmutableMap.of("data", articlePageInfo);
        return Result.of(200, map);
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(Article article) {
        articleService.insertArticle(article);
        return Result.of(200);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(Article article) {
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
