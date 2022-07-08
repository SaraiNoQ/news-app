package com.dysopia.dysopiaassistproject.backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dysopia.dysopiaassistproject.backend.pojo.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NewsMapper extends BaseMapper<News> {
    //清空指定表
    @Update("truncate table news")
    void deleteAllNews();
}
