package com.puff.mapper;

import com.puff.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 订单详情表 Mapper 接口
 * </p>
 *
 * @author puff
 * @since 2023-06-06
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}
