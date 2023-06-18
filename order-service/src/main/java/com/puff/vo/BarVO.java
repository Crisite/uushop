package com.puff.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BarVO {
    private List<String> name;
    private List<BarDataVO> values;
}
