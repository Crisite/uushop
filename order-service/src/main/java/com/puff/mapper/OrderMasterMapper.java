package com.puff.mapper;

import com.puff.entity.OrderMaster;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author puff
 * @since 2023-06-06
 */
@Mapper
public interface OrderMasterMapper extends BaseMapper<OrderMaster> {
//    mybatis默认使用参数下标作为参数名称对参数赋值，即0、1、2等等这些参数名称，只有当使用注解显示标注参数名称才会使用指定的参数名称
    boolean cancel(@Param("buyerId") Integer buyerId,@Param("orderId") String orderId);
    boolean finish(@Param("orderId") String orderId);
    boolean pay(@Param("buyerId") Integer buyerId,@Param("orderId") String orderId);
}
