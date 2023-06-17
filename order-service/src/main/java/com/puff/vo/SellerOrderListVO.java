package com.puff.vo;

import com.puff.entity.OrderMaster;
import lombok.Data;

import java.util.List;

@Data
public class SellerOrderListVO {
    private List<OrderMaster> content;
    private Long size;
    private Long total;
}
