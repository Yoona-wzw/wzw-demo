package com.wzw.wj.controller;

import com.wzw.wj.commons.Result;
import com.wzw.wj.commons.ResultFactory;
import com.wzw.wj.pojo.JotterArticle;
import com.wzw.wj.service.JotterArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName JotterController
 * @Description
 * @Author wzw
 * @Date 2020/5/28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api")
public class JotterController {

    @Autowired
    private JotterArticleService jotterArticleService;
    @PostMapping("/admin/content/books/covers")
    public String articleCovers(MultipartFile file){
        String folder="E:/photos";
        File imageFile = new File(folder);
        File f=new File(imageFile, com.wzw.wj.utils.StringUtils.getRandomString(6)+file.getOriginalFilename().substring(file.getOriginalFilename().length()-4));
        if(!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL="http://localhost:8843/api/file/"+f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    @PostMapping("/admin/content/article")
    public Result saveArticle(@RequestBody JotterArticle article) {
        jotterArticleService.addOrUpdate(article);
        return ResultFactory.buildSuccessResult("保存成功");
    }
    @GetMapping("/article/{size}/{page}")
    public Page pageArticleList(@PathVariable int size,@PathVariable int page){
        return jotterArticleService.list(page-1,size);
    }
}
