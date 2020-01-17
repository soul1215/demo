package com.tedued.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedued.demo.entity.PageResult;
import com.tedued.demo.entity.User;
import com.tedued.demo.service.UserService;
import com.tedued.demo.utils.JsonResult;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
@RequestMapping("/getUserInf")
    @ResponseBody
    public JsonResult<PageResult> getUserInf() {
	
	
	PageResult data = userService.getUserInfo();
    
        return new JsonResult<>(0,"完美",data);
    }


}
