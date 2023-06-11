package com.puff.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailVo {  private String detailId;

    private String orderId;

    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    /**
     * 商品小图
     */
    private String productIcon;

}
