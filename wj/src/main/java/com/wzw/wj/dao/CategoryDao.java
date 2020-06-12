package com.wzw.wj.dao;

import com.wzw.wj.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Interface CategoryDao
 * @Description
 * @Author wzw
 * @Date 2020/5/25
 * @Version 1.0
 **/
public interface CategoryDao extends JpaRepository<Category ,Integer> {
}
