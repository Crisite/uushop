package com.puff.service.impl;

import com.puff.entity.OrderDetail;
import com.puff.mapper.OrderDetailMapper;
import com.puff.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.puff.util.EChartsColorUtil;
import com.puff.vo.BarDataVO;
import com.puff.vo.BarResultVO;
import com.puff.vo.BarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public BarVO barData() {
        List<BarResultVO> barResultVOS = this.orderDetailMapper.barData();
        ArrayList<String> barVONameList = new ArrayList<>();
        ArrayList<BarDataVO> barDataVOList = new ArrayList<>();
        for (BarResultVO barResultVO : barResultVOS) {
            barVONameList.add(barResultVO.getName());
            BarDataVO barDataVO = new BarDataVO();
            barDataVO.setValue(barResultVO.getValue());
            Map<String, String> itemStyle = EChartsColorUtil.createItemStyle(barResultVO.getValue());
            barDataVO.setItemStyle(itemStyle);

            barDataVOList.add(barDataVO);
        }
        return new BarVO(barVONameList, barDataVOList);
    }
}
