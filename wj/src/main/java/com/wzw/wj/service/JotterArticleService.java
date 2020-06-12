package com.wzw.wj.service;

import com.wzw.wj.dao.JotterArticleDao;
import com.wzw.wj.pojo.JotterArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @ClassName JotterArticleService
 * @Description
 * @Author wzw
 * @Date 2020/5/28
 * @Version 1.0
 **/
@Service
public class JotterArticleService {
    @Autowired
    private JotterArticleDao jotterArticleDao;
   public JotterArticle addOrUpdate(JotterArticle jotterArticle){
       if(jotterArticle!=null) {
           try {
               JotterArticle save = jotterArticleDao.save(jotterArticle);
               return save;

           }catch (Exception e){
               e.printStackTrace();
           }
       }
       return null;
   }
   /*分页 page页码 size 页面大小*/
   public Page list(int page,int size){
       Sort sort = new Sort(Sort.Direction.DESC, "id");
       Page<JotterArticle> pageLists = jotterArticleDao.findAll(PageRequest.of(page, size, sort));
       return pageLists;
   }
}
