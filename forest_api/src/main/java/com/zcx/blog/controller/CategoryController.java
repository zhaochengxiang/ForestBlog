package com.zcx.blog.controller;

import com.google.common.collect.ImmutableMap;
import com.zcx.blog.entity.Article;
import com.zcx.blog.entity.Category;
import com.zcx.blog.entity.Result;
import com.zcx.blog.service.ArticleService;
import com.zcx.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(Category category) {
        categoryService.insert(category);
        return Result.of(200);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(Category category) {
        categoryService.update(category);
        return Result.of(200);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(Integer id) {
        categoryService.deleteCategory(id);
        return Result.of(200);
    }

    @RequestMapping(value = "/getAll")
    @ResponseBody
    public String getAll() {
        List<Category> categories = categoryService.listCategory();
        return Result.of(200,categories);
    }
}
