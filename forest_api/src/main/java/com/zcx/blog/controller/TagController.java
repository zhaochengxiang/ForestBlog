package com.zcx.blog.controller;

import com.zcx.blog.entity.Result;
import com.zcx.blog.entity.Tag;
import com.zcx.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(Tag tag) {
        tagService.insert(tag);
        return Result.of(200);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(Tag tag) {
        tagService.update(tag);
        return Result.of(200);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(Integer id) {
        tagService.deleteById(id);
        return Result.of(200);
    }

    @RequestMapping(value = "/getAll")
    @ResponseBody
    public String getAll() {
        List<Tag> tags = tagService.listTag();
        return Result.of(200,tags);
    }

    @RequestMapping(value = "/getAllWithArticleCount")
    @ResponseBody
    public String getAllWithArticleCount() {
        List<Tag> tags = tagService.listTagWithCount();
        return Result.of(200,tags);
    }
}
