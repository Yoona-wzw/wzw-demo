package com.wzw.wj.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzw.wj.pojo.Music;
import com.wzw.wj.pojo.Playlist;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HttpConnect
 * @Description
 * @Author wzw
 * @Date 2020/6/5
 * @Version 1.0
 **/
public class HttpConnect {
    private static CloseableHttpClient httpClient;
    static{//httpclient pool
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(230);
        cm.setDefaultMaxPerRoute(100);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }
    public static Map<?,?> getResult(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity);
            JSONObject jsonObject = JSON.parseObject(data);
            return (Map<?, ?>) jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Map result = getResult("https://blogme.top:3000/top/list?idx=2");
        Map playlist = (Map)result.get("playlist");
        List tracks =(List) playlist.get("tracks");
        Map o =(Map) tracks.get(0);
        System.out.println(tracks);
    }
}
