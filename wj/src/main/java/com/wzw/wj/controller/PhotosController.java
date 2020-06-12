package com.wzw.wj.controller;

import com.wzw.wj.pojo.Photos;
import com.wzw.wj.service.PhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName PhotosController
 * @Description
 * @Author wzw
 * @Date 2020/6/3
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/photos")
public class PhotosController {
    @Autowired
    private PhotosService photosService;
    @GetMapping("/search")
    public List<Photos> searchPhotos(@RequestParam("q")String q,@RequestParam("page") int page){
        List<Photos> photos = photosService.getPhotos(page,q);
        return photos;
    }
}
