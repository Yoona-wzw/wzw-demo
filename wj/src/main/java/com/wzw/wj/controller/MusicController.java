package com.wzw.wj.controller;

import com.wzw.wj.pojo.Music;
import com.wzw.wj.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName MusicController
 * @Description
 * @Author wzw
 * @Date 2020/6/3
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/music")
public class MusicController {
    @Autowired
    private MusicService musicService;
    @GetMapping("/searchMusic")
    public List<Music> searchMusic(@RequestParam("type") int type){
        List<Music> resultSong = musicService.getMusicHot(type);
        return resultSong;
    }
    @GetMapping("/searchUrlLyr")
    public Music searchUrlLyrById(@RequestParam("id") int id){
        String musicUrl = musicService.getMusicUrl(id);
        String musiclrc = musicService.getMusiclrc(id);
        Music music=new Music();
        music.setUrl(musicUrl);
        music.setLrc(musiclrc);
        return music;
    }
    @GetMapping("/searchKey")
    public List<Music> searchKey(@RequestParam("key") String key){
        List<Music> music = musicService.searchMusic(key);
        return music;
    }
}
