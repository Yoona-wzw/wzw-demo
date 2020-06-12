package com.wzw.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName Category
 * @Description 图书类别
 * @Author wzw
 * @Date 2020/5/25
 * @Version 1.0
 **/
@Entity
@Table(name = "category")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String name;
}
