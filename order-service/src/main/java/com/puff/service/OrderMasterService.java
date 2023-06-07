package com.puff.service;

import com.puff.entity.OrderMaster;
import com.baomidou.mybatisplus.extension.service.IService;
import com.puff.feign.ProductFeign;
import com.puff.form.BuyerOrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author puff
 * @since 2023-06-06
 */
public interface OrderMasterService extends IService<OrderMaster> {
    Boolean create(BuyerOrderForm buyerOrderForm);
}
