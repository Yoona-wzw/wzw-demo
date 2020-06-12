package com.wzw.wj.pojo;

import lombok.Data;

/**
 * @ClassName Music
 * @Description
 * @Author wzw
 * @Date 2020/6/5
 * @Version 1.0
 **/
@Data
public class Music {
    private int id;
   private String name;
   private Artist ar;
   private String url;
   private Playlist playlist;
   private MusicPic al;
   private String title;
   private String artist;
   private String src;
   private String pic;
   private String lrc;
}
