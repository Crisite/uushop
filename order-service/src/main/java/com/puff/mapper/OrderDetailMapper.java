package com.puff.mapper;

import com.puff.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puff.vo.BarLineResultVO;
import com.puff.vo.BarLineVO;
import com.puff.vo.BarResultVO;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

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
    List<BarResultVO> barData();
    List<BarLineResultVO> basicLineData();
}
