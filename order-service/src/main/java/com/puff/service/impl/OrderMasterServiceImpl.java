package com.puff.service.impl;

import com.puff.entity.OrderMaster;
import com.puff.feign.ProductFeign;
import com.puff.form.BuyerOrderForm;
import com.puff.mapper.OrderMasterMapper;
import com.puff.service.OrderMasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author puff
 * @since 2023-06-06
 */
@Service
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements OrderMasterService {

    @Autowired
    private ProductFeign productFeign;

    public Boolean create(BuyerOrderForm buyerOrderForm) {
        BigDecimal priceById = productFeign.findPriceById(1);
        return null;
    }
}
