package com.puff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.puff.vo.OrderDetailVo;
import com.puff.vo.OrderMasterVo;
import com.puff.vo.SellerOrderListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Override
    public List<OrderMaster> list(Integer buyerId, Integer page, Integer size) {
        Page<OrderMaster> orderMasterPage = new Page<>(page, size);
        QueryWrapper<OrderMaster> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_openid", buyerId);

        Page<OrderMaster> resultPage = page(orderMasterPage, queryWrapper);

        System.out.println(resultPage.getOrders());

        return resultPage.getRecords();
    }

    @Override
    public OrderMasterVo detail(Integer buyerId, String orderId) {
        QueryWrapper<OrderMaster> orderMasterQueryWrapper = new QueryWrapper<>();
        orderMasterQueryWrapper.eq("buyerId", buyerId);
        orderMasterQueryWrapper.eq("order_id", orderId);
        OrderMaster orderMaster = this.orderMasterMapper.selectOne(orderMasterQueryWrapper);
        OrderMasterVo orderMasterVo = new OrderMasterVo();
        BeanUtils.copyProperties(orderMaster, orderMasterVo);

        QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
        orderDetailQueryWrapper.eq("order_id", orderId);
        List<OrderDetail> orderDetailList = this.orderDetailMapper.selectList(orderDetailQueryWrapper);
        ArrayList<OrderDetailVo> orderDetailVoList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            OrderDetailVo orderDetailVo = new OrderDetailVo();
            BeanUtils.copyProperties(orderDetail, orderDetailVo);
            orderDetailVoList.add(orderDetailVo);
        }
        orderMasterVo.setOrderDetailList(orderDetailVoList);

        return orderMasterVo;
    }

    @Override
    public boolean cancel(Integer buyerId, String orderId) {

        QueryWrapper<OrderDetail> orderMasterQueryWrapper = new QueryWrapper<>();
        orderMasterQueryWrapper.eq("order_id", orderId);
        List<OrderDetail> orderDetailList = this.orderDetailMapper.selectList(orderMasterQueryWrapper);
        for (OrderDetail orderDetail : orderDetailList) {
            this.productFeign.addStockById(buyerId, orderDetail.getProductQuantity());
        }

        return this.orderMasterMapper.cancel(buyerId, orderId);
    }

    @Override
    public boolean finish(String orderId) {
        return this.orderMasterMapper.finish(orderId);
    }

    @Override
    public boolean pay(Integer buyerId, String orderId) {
        return this.orderMasterMapper.pay(buyerId, orderId);
    }

    @Override
    public Boolean sellerCancel(String orderId) {
        return this.orderMasterMapper.sellerCancel(orderId);
    }


}
