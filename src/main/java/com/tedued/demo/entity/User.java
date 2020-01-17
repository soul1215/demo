package com.tedued.demo.entity;

import com.github.pagehelper.PageInfo;

import lombok.Data;

@Data
public class User extends PageInfo<User>{
    private long id;
    private String name;
    private int age;
    private  String email;
    
    


}
