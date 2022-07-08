package com.dysopia.dysopiaassistproject.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dysopia.dysopiaassistproject.backend.pojo.NewsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface NewsTypeMapper extends BaseMapper<NewsType> {
    //清空指定表
    @Update("truncate table news_type")
    void deleteAllNewsType();
}
