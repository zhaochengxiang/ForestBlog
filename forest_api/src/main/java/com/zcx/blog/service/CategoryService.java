package com.zcx.blog.service;

import com.zcx.blog.entity.Category;
import com.zcx.blog.mapper.ArticleCategoryRefMapper;
import com.zcx.blog.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    @Autowired(required = false)
    private ArticleCategoryRefMapper articleCategoryRefMapper;

    public void insert(Category category) { categoryMapper.insert(category); }

    public void update(Category category) { categoryMapper.update(category); }

    public void deleteCategory(Integer id) { categoryMapper.deleteCategory(id); }

    public List<Category> listCategory() { return categoryMapper.listCategory(); }

    public List<Category> listCategoryWithCount() {
        List<Category> categoryList = categoryMapper.listCategory();
        for (int i = 0; i < categoryList.size(); i++) {
            Integer count = articleCategoryRefMapper.countArticleByCategoryId(categoryList.get(i).getCategoryId());
            categoryList.get(i).setArticleCount(count);
        }

        return categoryList;
    }

}
