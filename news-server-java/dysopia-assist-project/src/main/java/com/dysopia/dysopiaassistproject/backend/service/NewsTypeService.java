package com.dysopia.dysopiaassistproject.backend.service;

import com.dysopia.dysopiaassistproject.backend.pojo.NewsType;

import java.util.List;

public interface NewsTypeService {
    public int addNewsType(NewsType item);
    public int deleteNewsType(Integer id);

    public void deleteAll();
    public List<NewsType> getAllTypes();
}
