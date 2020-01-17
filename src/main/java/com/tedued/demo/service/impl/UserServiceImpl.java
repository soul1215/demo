package com.tedued.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tedued.demo.entity.PageResult;
import com.tedued.demo.entity.User;
import com.tedued.demo.mapper.UserMapper;
import com.tedued.demo.service.UserService;
import com.tedued.demo.utils.PageUtils;
@Service
public class UserServiceImpl implements UserService {

@Autowired
private UserMapper userMapper;
    @Override
    public  PageResult getUserInfo() {
    	User user =new User();
    	int pageNum = user.getPageNum() == 0 ? 1 : user.getPageNum();
		int pageSize = user.getPageSize() == 0 ? 2 : user.getPageSize();
		PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.getUserInfo();
        PageInfo<User> info = new PageInfo<User>(list);
        System.err.println(list);

        return PageUtils.getPageResult(info);

    }
}
