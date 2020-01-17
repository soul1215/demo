package com.tedued.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class PageResult {

  
    private int pageNum;

    private int pageSize;

    private long totalSize;

    private int totalPages;

    private List<?> content;
}
