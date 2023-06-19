package com.puff.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BarLineVO {
    private List<String> names;
    private List<Integer> values;
}
