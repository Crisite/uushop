package com.puff.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuyerProductInfoVo {
    private Integer productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer quantity = 0;
}
