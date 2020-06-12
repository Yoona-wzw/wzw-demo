package com.wzw.wj.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzw.wj.pojo.Photos;
import com.wzw.wj.utils.HttpConnect;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PhotosService
 * @Description
 * @Author wzw
 * @Date 2020/6/3
 * @Version 1.0
 **/
@Service
@Slf4j
public class PhotosService {
    public List<Photos> getPhotos(Integer page,String q) {
        Map<?, ?> result = HttpConnect.getResult("https://wallhaven.cc/api/v1/search?q="+q+"&page=" + page);
        try {
            List data =(List) result.get("data");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<Photos> photos = new PhotosService().getPhotos(1,"");
        System.out.println(photos);
    }

}
