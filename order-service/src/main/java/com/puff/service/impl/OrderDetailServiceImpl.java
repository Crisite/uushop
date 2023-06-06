package com.puff.service.impl;

import com.puff.entity.OrderDetail;
import com.puff.mapper.OrderDetailMapper;
import com.puff.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author puff
 * @since 2023-06-06
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
