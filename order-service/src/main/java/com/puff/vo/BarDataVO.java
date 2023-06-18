package com.puff.vo;

import lombok.Data;

import java.util.Map;

@Data
public class BarDataVO {
    private Integer value;
    private Map<String, String> itemStyle;
}
