package com.puff.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

@Data
public class BuyerProductCategoryVO {
    private String name;
    private Integer type;
    private List<BuyerProductInfoVo> goods;
}
