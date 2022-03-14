package com.liuzhuowen.bolg.model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Article {
    public Integer aid;
    public Integer uid;
    public String title;
    public String type;
    public String content;
    public LocalDate publishedAt;

    public Article(){}

    public Article(Integer aid, Integer uid, String title, String type, String content, LocalDate publishedAt) {
        this.aid = aid;
        this.uid = uid;
        this.title = title;
        this.type = type;
        this.content = content;
        this.publishedAt = publishedAt;
    }
    public String getPublicshedAt(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return publishedAt.format(formatter);

    }

    public String getDesc(){
        //可以选取文字作为文章摘要
        int length = Integer.min(content.length(),20);
        return content.substring(0,length);
    }

    @Override
    public String toString() {
        return "Article{" +
                "aid=" + aid +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
