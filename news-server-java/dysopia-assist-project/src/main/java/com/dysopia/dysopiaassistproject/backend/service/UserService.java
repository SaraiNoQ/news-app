package com.dysopia.dysopiaassistproject.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dysopia.dysopiaassistproject.backend.pojo.User;

import java.util.List;

public interface UserService {
    public int addUser(User item);
    public int deleteUser(Integer id);

    public int editUser(User item);
    public User selectUser(Integer id);

    public User isExist(QueryWrapper<User> queryWrapper);

    public List<User> getAll();
}
