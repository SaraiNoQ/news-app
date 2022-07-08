package com.dysopia.dysopiaassistproject.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ：Abean
 * @date ：2022/6/25 23:54
 * @description ：新闻种类实体类
 */

@Data
@TableName("news_type")
public class NewsType {
    @TableId
    private Integer id;
    @TableField("type")
    private String type;
    @TableField("uri")
    private String uri;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
