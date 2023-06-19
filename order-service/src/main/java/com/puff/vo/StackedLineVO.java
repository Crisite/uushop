package com.puff.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StackedLineVO {
    private List<String> names;
    private List<String> dates;
    private List<StackedLineInnerVO> datas;
}
