package com.dysopia.dysopiaassistproject.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dysopia.dysopiaassistproject.backend.mapper.NewsMapper;
import com.dysopia.dysopiaassistproject.backend.pojo.News;
import com.dysopia.dysopiaassistproject.backend.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author ：Abean
 * @date ：2022/6/28 14:59
 * @description ：TODO
 */


@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    @Transactional
    public void deleteAll() {
        newsMapper.deleteAllNews();
    }

    @Override
    @Transactional
    public List<News> getNewsByType(Map<String, Object> map) {
        return newsMapper.selectByMap(map);
    }

    @Override
    @Transactional
    public News getNewsByTitle(QueryWrapper<News> queryWrapper) {
        return newsMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public int addNews(News item) {
        return newsMapper.insert(item);
    }

    @Override
    @Transactional
    public int deleteNews(Integer id) {
        return newsMapper.deleteById(id);
    }

    @Override
    @Transactional
    public int updateNews(News item) {
        return newsMapper.updateById(item);
    }

    @Override
    @Transactional
    public List<News> getAll() {
        return newsMapper.selectList(null);
    }
}
