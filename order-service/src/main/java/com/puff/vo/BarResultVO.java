package com.puff.vo;

import lombok.Data;

import java.util.List;
/*
    在mapperxml中需要用别名将数据库字段名与实体属性对应起来 eg:product_name name
 */
@Data
public class BarResultVO {
    private String name;
    private Integer value;
}
