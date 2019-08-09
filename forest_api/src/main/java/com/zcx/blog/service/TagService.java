package com.zcx.blog.service;

import com.zcx.blog.entity.Category;
import com.zcx.blog.entity.Tag;
import com.zcx.blog.mapper.ArticleCategoryRefMapper;
import com.zcx.blog.mapper.ArticleTagRefMapper;
import com.zcx.blog.mapper.CategoryMapper;
import com.zcx.blog.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {
    @Autowired(required = false)
    private TagMapper tagMapper;

    @Autowired(required = false)
    private ArticleTagRefMapper articleTagRefMapper;

    public void insert(Tag tag) { tagMapper.insert(tag); }

    public void update(Tag tag) { tagMapper.update(tag); }

    public void deleteById(Integer id) { tagMapper.deleteById(id); }

    public List<Tag> listTag() { return tagMapper.listTag(); }

    public List<Tag> listTagWithCount() {

        List<Tag> tagList = tagMapper.listTag();
        for (int i = 0; i < tagList.size(); i++) {
            Integer count = articleTagRefMapper.countArticleByTagId(tagList.get(i).getTagId());
            tagList.get(i).setArticleCount(count);
        }

        return tagList;
    }
}
