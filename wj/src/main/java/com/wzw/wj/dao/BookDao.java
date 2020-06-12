package com.wzw.wj.dao;

import com.wzw.wj.pojo.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wzw.wj.pojo.Category;
import java.util.List;

/**
 * @Interface BookDao
 * @Description
 * @Author wzw
 * @Date 2020/5/25
 * @Version 1.0
 **/
public interface BookDao extends JpaRepository<Book,Integer> {
    List<Book> findAllByCategory(Category category);//根据类别查询
    List<Book> findAllByTitleLikeOrAuthorLike(String k1,String k2);//书名或作者名模糊查
}
