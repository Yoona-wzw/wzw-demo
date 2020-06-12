package com.wzw.wj.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName JotterArticle
 * @Description
 * @Author wzw
 * @Date 2020/5/28
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "jotter_article")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class JotterArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String articleTitle;
    private String articleContentHtml;
    private String articleContentMd;
    private String articleAbstract;
    private String articleCover;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private String articleDate;


}
