package com.dysopia.dysopiaassistproject.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dysopia.dysopiaassistproject.backend.pojo.News;

import java.util.List;
import java.util.Map;

public interface NewsService {
    public int addNews(News item);
    public int deleteNews(Integer id);
    public int updateNews(News item);

    public void deleteAll();
    public List<News> getNewsByType(Map<String, Object> map);
    public News getNewsByTitle(QueryWrapper<News> queryWrapper);
    public List<News> getAll();
}
