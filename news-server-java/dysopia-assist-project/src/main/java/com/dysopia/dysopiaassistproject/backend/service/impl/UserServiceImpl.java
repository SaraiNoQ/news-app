package com.dysopia.dysopiaassistproject.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dysopia.dysopiaassistproject.backend.mapper.UserMapper;
import com.dysopia.dysopiaassistproject.backend.pojo.News;
import com.dysopia.dysopiaassistproject.backend.pojo.User;
import com.dysopia.dysopiaassistproject.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：Abean
 * @date ：2022/7/5 11:18
 * @description ：TODO
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public int addUser(User item) {
        return userMapper.insert(item);
    }

    @Override
    @Transactional
    public int deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    @Transactional
    public int editUser(User item) {
        return userMapper.updateById(item);
    }

    @Override
    @Transactional
    public User selectUser(Integer id) {
        return (User) userMapper.selectById(id);
    }

    @Override
    @Transactional
    public User isExist(QueryWrapper<User> queryWrapper) {
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return userMapper.selectList(null);
    }
}
