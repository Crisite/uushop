package com.puff.service;

import com.puff.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.puff.vo.BarLineVO;
import com.puff.vo.BarVO;
import com.puff.vo.StackedLineVO;

/**
 * <p>
 * 订单详情表 服务类
 * </p>
 *
 * @author puff
 * @since 2023-06-06
 */
public interface OrderDetailService extends IService<OrderDetail> {
    BarVO barData();
    BarLineVO basicLineSale();
    StackedLineVO stackedLineData();
}
