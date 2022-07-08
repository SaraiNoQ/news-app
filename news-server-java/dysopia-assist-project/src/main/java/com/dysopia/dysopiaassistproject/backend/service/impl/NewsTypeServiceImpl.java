package com.dysopia.dysopiaassistproject.backend.service.impl;

import com.dysopia.dysopiaassistproject.backend.mapper.NewsTypeMapper;
import com.dysopia.dysopiaassistproject.backend.pojo.NewsType;
import com.dysopia.dysopiaassistproject.backend.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：Abean
 * @date ：2022/6/26 0:13
 * @description ：TODO
 */

@Service
public class NewsTypeServiceImpl implements NewsTypeService {

    @Autowired
    private NewsTypeMapper newsTypeMapper;

    @Override
    @Transactional
    public int addNewsType(NewsType item) {
        return newsTypeMapper.insert(item);
    }

    @Override
    @Transactional
    public int deleteNewsType(Integer id) {
        return newsTypeMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        newsTypeMapper.deleteAllNewsType();
    }

    @Override
    @Transactional
    public List<NewsType> getAllTypes() {
        return newsTypeMapper.selectList(null);
    }
}
