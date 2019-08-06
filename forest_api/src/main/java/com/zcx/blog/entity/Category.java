package com.zcx.blog.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 6687286913317513141L;

    private Integer categoryId;

    private Integer categoryPid;

    private String categoryName;

    private String categoryDescription;

    private Integer categoryOrder;

    private String categoryIcon;

    /**
     * 文章数量(非数据库字段)
     */
    private Integer articleCount;
}
