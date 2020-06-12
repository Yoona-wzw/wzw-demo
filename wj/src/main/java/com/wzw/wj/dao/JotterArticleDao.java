package com.wzw.wj.dao;

import com.wzw.wj.pojo.JotterArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Interface JotterArticleDao
 * @Description
 * @Author wzw
 * @Date 2020/5/28
 * @Version 1.0
 **/
@Repository
public interface JotterArticleDao extends JpaRepository<JotterArticle,Integer> {

}
