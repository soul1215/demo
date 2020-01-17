package com.tedued.demo.mapper;

import java.util.List;

import com.tedued.demo.entity.User;

public interface UserMapper {
    /**
     * 获取用户信息
     * @return
     */
    List<User> getUserInfo();

}
