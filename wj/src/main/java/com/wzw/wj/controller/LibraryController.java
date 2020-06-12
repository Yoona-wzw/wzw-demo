package com.wzw.wj.controller;

import com.wzw.wj.pojo.Book;
import com.wzw.wj.service.BookService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName LibraryController
 * @Description
 * @Author wzw
 * @Date 2020/5/25
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api")
public class LibraryController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> bookList(){
        return bookService.list();
    }
    @PostMapping("/books")
    public Book addOrUpdate(@RequestBody Book book){
        bookService.addOrUpdate(book);
        return book;
    }
    @DeleteMapping("/books")
    public void deleteBook(@RequestParam("id") int id){
        bookService.deleteById(id);
    }
    @GetMapping("/categories/{cid}/books")
    public List<Book> bookListByCategory(@PathVariable int cid){
        if(cid !=0){
            return bookService.listByCategroy(cid);
        }else{
            return bookList();
        }

    }
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam("keywords") String keywords){
        if(StringUtils.isNotBlank(keywords)){
            List<Book> books = bookService.searchBooks(keywords);
            return books;
        }
        return bookList();
    }
    /*图片上传*/
    @PostMapping("/covers")
    public String coversUpload(MultipartFile file){
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

}
