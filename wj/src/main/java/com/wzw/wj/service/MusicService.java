package com.wzw.wj.service;

import com.wzw.wj.pojo.Lrc;
import com.wzw.wj.pojo.Music;
import com.wzw.wj.utils.HttpConnect;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MusicService
 * @Description
 * @Author wzw
 * @Date 2020/6/3
 * @Version 1.0
 **/
@Service
public class MusicService {

    public List selectSong(String key,int page){
        CloseableHttpClient build = HttpClientBuilder.create().build();
        return null;
    }
    /*获取歌曲url*/
    public String getMusicUrl(int id){
        String url="https://blogme.top:3000/song/url?id=";
        Map result =(Map)HttpConnect.getResult(url + id);
         List msUrl =(List) result.get("data");
        Map Mapurl =(Map) msUrl.get(0);
        Object musicUrl = Mapurl.get("url");
        if(musicUrl==null){
            return "";
        }
        return musicUrl.toString();
    }
    /*获取歌词*/
    public String getMusiclrc(int id){
        String url="https://blogme.top:3000/lyric?id=";
        Map result =(Map) HttpConnect.getResult(url + id);
        Object lrc = result.get("lrc");
        if(lrc==null){
            return "暂无";
        }else{
            Map lrcMap=(Map) lrc;
            String lyric =lrcMap.get("lyric").toString();
            return lyric;
        }
    }
    /*获取歌曲详情*/
    public List getMusicInfo(int id){
        String url="https://blogme.top:3000/song/detail?ids=";
        Object result =HttpConnect.getResult(url + id);
        return null;
    }
    /*获取热门歌曲*/
    public List<Music> getMusicHot(int id){
        String url="https://blogme.top:3000/top/list?idx=";
         Map result =(Map) HttpConnect.getResult(url + id);
         if(result==null){
             return null;
         }
        Map playlist = (Map)result.get("playlist");
        List tracks =(List) playlist.get("tracks");
        List<Music> list=new ArrayList<>();
        MusicService musicService = new MusicService();
        for(Object tr:tracks) {
            Music music = new Music();
            Map rest = (Map) tr;
                music.setTitle(rest.get("name").toString());
                music.setId((int) rest.get("id"));
                List ar = (List) rest.get("ar");
                Map arMap = (Map) ar.get(0);
                music.setArtist(arMap.get("name").toString());
                Map al = (Map) rest.get("al");
                music.setPic(al.get("picUrl").toString());
              //  music.setUrl(musicService.getMusicUrl(music.getId()));
              //  music.setLrc(musicService.getMusiclrc(music.getId()));
                list.add(music);
            }
        return list;
    }
    public List<Music> searchMusic(String key){
        String url="https://blogme.top:3000/search?keywords="+key;
        Map<?, ?> result = HttpConnect.getResult(url);
        if(result==null){
            return null;
        }
        Map result1 =(Map) result.get("result");
        List songs =(List) result1.get("songs");
        List<Music> list=new ArrayList<>();
        for(Object ls:songs) {
            Music music = new Music();
            Map rest = (Map) ls;
            music.setTitle(rest.get("name").toString());
            music.setId((int) rest.get("id"));
            List ar = (List) rest.get("artists");
            Map arMap = (Map) ar.get(0);
            music.setArtist(arMap.get("name").toString());
            if(arMap.get("picUrl")==null){
                music.setPic("");
            }else{
                music.setPic(arMap.get("picUrl").toString());

            }
            list.add(music);
        }
        return list;
    }

    public static void main(String[] args) {
        List<Music> musicHot = new MusicService().searchMusic("海阔天空");
        System.out.println(musicHot);
    }

}
