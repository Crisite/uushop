package com.puff.service;

import com.puff.entity.OrderMaster;
import com.baomidou.mybatisplus.extension.service.IService;
import com.puff.feign.ProductFeign;
import com.puff.form.BuyerOrderForm;
import com.puff.vo.OrderMasterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author puff
 * @since 2023-06-06
 */
public interface OrderMasterService extends IService<OrderMaster> {
    String create(BuyerOrderForm buyerOrderForm);
    List<OrderMaster> list(Integer buyerId, Integer page, Integer size);
    OrderMasterVo detail(Integer buyerId, String orderId);
    boolean cancel(Integer buyerId, String orderId);
    boolean finish(String orderId);
    boolean pay(Integer buyerId, String orderId);
}
