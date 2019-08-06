package com.zcx.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Article {
    private static final long serialVersionUID = 5207865247400761539L;

    private Integer articleId;
    private String articleTitle;

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    //    private Integer articleId;
//
//    private Integer articleUserId;
//
//    private String articleTitle;
//
//    private Integer articleViewCount;
//
//    private Integer articleCommentCount;
//
//    private Integer articleLikeCount;
//
//    private Date articleCreateTime;
//
//    private Date articleUpdateTime;
//
//    private Integer articleIsComment;
//
//    private Integer articleStatus;
//
//    private Integer articleOrder;
//
//    private String articleContent;
//
//    private String articleSummary;
//
//    private User user;
//
//    private List<Tag> tagList;
//
//    private List<Category> categoryList;

}
