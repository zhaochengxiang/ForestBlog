package com.zcx.blog.entity;

import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Article implements Serializable {
    private static final long serialVersionUID = 5207865247400761539L;

    private Integer articleId;

    private Integer articleUserId;

    private String articleTitle;

    private Integer articleViewCount;

    private Integer articleCommentCount;

    private Integer articleLikeCount;

    private Date articleCreateTime;

    private Date articleUpdateTime;

    private Integer articleIsComment;

    private Integer articleStatus;

    private Integer articleOrder;

    private String articleContent;

    private String articleSummary;

    private User user;

    private List<Tag> tagList;

    private List<Category> categoryList;

    public Article(ArticleParam articleParam) {

        if (articleParam.getArticleId() != null) {
            this.articleId = articleParam.getArticleId();
        }

        this.articleTitle = articleParam.getArticleTitle();
        this.articleContent = articleParam.getArticleContent();
        this.articleStatus = articleParam.getArticleStatus();

        if (articleParam.getArticleOrder() != null) {
            this.articleOrder = articleParam.getArticleOrder();
        }

        List<Category> categoryList = new ArrayList<>();
        if (articleParam.getArticleParentCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }

        this.categoryList = categoryList;
    }

}
