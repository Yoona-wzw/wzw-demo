package com.wzw.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName Book
 * @Description
 * @Author wzw
 * @Date 2020/5/25
 * @Version 1.0
 **/
@Entity
@Table(name = "book")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "cid")
    private Category category;
    String cover;
    String title;
    String author;
    String date;
    String press;
    String abs;
}
