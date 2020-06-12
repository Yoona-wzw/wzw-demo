package com.wzw.wj.service;

import com.wzw.wj.dao.BookDao;
import com.wzw.wj.pojo.Book;
import com.wzw.wj.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BookService
 * @Description
 * @Author wzw
 * @Date 2020/5/25
 * @Version 1.0
 **/
@Service
public class BookService {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private CategoryService categoryService;
/**
 * 查出所有书籍
 * @Param []
 * @return
**/
    public List<Book> list(){
        Sort id = new Sort(Sort.Direction.DESC, "id");
        List<Book> all = bookDao.findAll(id);
        return all;

    }
    /**
     * 添加或更新书籍 当主键存在时更新 不存在时添加
     * @Param [book]
     * @return
    **/
    public void addOrUpdate(Book book){
         bookDao.save(book);
    }
    /**
     * 删除
     * @Param [id]
     * @return
    **/
    public void deleteById(int id){
        bookDao.deleteById(id);
    }
    /**
     * 根据类别查询
     * @Param [cid]
     * @return
    **/
    public List<Book> listByCategroy(int cid){
        Category category = categoryService.get(cid);
        List<Book> allByCategory = bookDao.findAllByCategory(category);
        return allByCategory;
    }
    public List<Book> searchBooks(String k1){
        List<Book> bookList = bookDao.findAllByTitleLikeOrAuthorLike('%'+k1+'%','%'+k1+'%');
        return bookList;
    }
}
