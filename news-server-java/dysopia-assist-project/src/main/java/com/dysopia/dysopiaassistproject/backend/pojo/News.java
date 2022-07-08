package com.dysopia.dysopiaassistproject.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author ：Abean
 * @date ：2022/6/28 14:46
 * @description ：新闻实体类
 */

@Data
@TableName("news")
public class News {
    @TableId
    private Integer id;
    @TableField("title")
    private String title;
    @TableField("author")
    private String author;
    @TableField("type")
    private String type;
    @TableField("content")
    private String content;
    @TableField("created_date")
    private Date created_date;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getCreated_date() {
        return created_date;
    }
}
