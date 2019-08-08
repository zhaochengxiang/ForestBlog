package com.zcx.blog.entity;

import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {
    private Integer articleId;

    private String articleTitle;

    private String articleContent;

    private Integer articleParentCategoryId;

    private Integer articleChildCategoryId;

    private Integer articleOrder;

    private Integer articleStatus;

    private List<Integer> articleTagIds;
}
