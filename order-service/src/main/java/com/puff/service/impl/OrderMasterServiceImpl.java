package com.puff.service.impl;

import com.puff.entity.OrderDetail;
import com.puff.entity.OrderMaster;
import com.puff.entity.ProductInfo;
import com.puff.feign.ProductFeign;
import com.puff.form.BuyerOrderForm;
import com.puff.form.BuyerOrderDetailForm;
import com.puff.mapper.OrderDetailMapper;
import com.puff.mapper.OrderMasterMapper;
import com.puff.service.OrderMasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
    @Autowired
    private OrderMasterMapper orderMasterMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public String create(BuyerOrderForm buyerOrderForm) {

//        数据存入OrderMaster
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerName(buyerOrderForm.getName());
        orderMaster.setBuyerPhone(buyerOrderForm.getPhone());
        orderMaster.setBuyerAddress(buyerOrderForm.getAddress());
        orderMaster.setBuyerOpenid(buyerOrderForm.getId());
        List<BuyerOrderDetailForm> items = buyerOrderForm.getItems();
        BigDecimal orderAmount = new BigDecimal(0);
        for (BuyerOrderDetailForm item : items) {
            Integer productId = item.getProductId();
            Integer productQuantity = item.getProductQuantity();
            
            BigDecimal price = productFeign.findPriceById(productId);
            BigDecimal amount = price.multiply(new BigDecimal(productQuantity));
            orderAmount = orderAmount.add(amount);
        }
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        orderMaster.setOrderAmount(orderAmount);
        this.orderMasterMapper.insert(orderMaster);

//        数据存入OrderDetail
        for (BuyerOrderDetailForm item : items) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderMaster.getOrderId());
            orderDetail.setProductId(item.getProductId());
            orderDetail.setProductQuantity(item.getProductQuantity());
            ProductInfo productInfo = this.productFeign.findById(item.getProductId());
            BeanUtils.copyProperties(productInfo, orderDetail);
            this.orderDetailMapper.insert(orderDetail);
            this.productFeign.subStockById(item.getProductId(), item.getProductQuantity());
        }

        return orderMaster.getOrderId();
    }
}
