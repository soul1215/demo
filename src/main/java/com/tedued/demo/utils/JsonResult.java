package com.tedued.demo.utils;

import lombok.Data;

@Data
public class JsonResult<E> {

    private Integer state; // 状态
    private String message; // 错误描述
    private E data; // 数据
   public JsonResult(E data){
        this.data = data;
        
    }
public JsonResult(Integer state, String message, E data) {
	super();
	this.state = state;
	this.message = message;
	this.data = data;
}
   

}
