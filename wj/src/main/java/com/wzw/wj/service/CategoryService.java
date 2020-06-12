package com.wzw.wj.service;

import com.wzw.wj.dao.CategoryDao;
import com.wzw.wj.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CategoryService
 * @Description
 * @Author wzw
 * @Date 2020/5/25
 * @Version 1.0
 **/
@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    public List<Category> list(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDao.findAll(sort);
    }
    public Category get(int id){
        Category category = categoryDao.findById(id).orElse(null);
        return category;
    }
}
