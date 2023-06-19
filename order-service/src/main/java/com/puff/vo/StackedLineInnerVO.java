package com.puff.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
public class StackedLineInnerVO {
    private String name;
    private final String type = "line";
    private final String stack = "销量";
    private List<Integer> data;
}
