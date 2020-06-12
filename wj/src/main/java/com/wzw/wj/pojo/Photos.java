package com.wzw.wj.pojo;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName Photos
 * @Description
 * @Author wzw
 * @Date 2020/6/3
 * @Version 1.0
 **/
@Data
public class Photos {
    private Integer favorites;
    private String purity;
    private String created_at;
    private Integer dimension_x;
    private String source;
    private String resolution;
    private String url;
    private Integer file_size;
    private String[] colors;
    private String short_url;
    private String path;
    private Integer dimension_y;
    private String file_type;
    private String id;
    private String category;
    private Integer views;
    private String ratio;
    private Map<String,String> thumbs;

}
