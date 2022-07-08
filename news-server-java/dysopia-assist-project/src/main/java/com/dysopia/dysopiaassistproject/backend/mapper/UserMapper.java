package com.dysopia.dysopiaassistproject.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dysopia.dysopiaassistproject.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {
}
