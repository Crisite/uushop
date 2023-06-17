package com.puff.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puff.entity.OrderMaster;
import com.puff.service.OrderMasterService;
import com.puff.utils.ResultVOUtil;
import com.puff.vo.ResultVO;
import com.puff.vo.SellerOrderListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller/order")
public class SellerOrderController {
    @Autowired
    private OrderMasterService orderMasterService;

    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable Integer page, @PathVariable Integer size) {
        Page<OrderMaster> orderMasterPage = new Page<>();
        Page<OrderMaster> resultPage = this.orderMasterService.page(orderMasterPage);
        SellerOrderListVO sellerOrderListVO = new SellerOrderListVO();
        sellerOrderListVO.setContent(resultPage.getRecords());
        sellerOrderListVO.setSize(resultPage.getSize());
        sellerOrderListVO.setTotal(resultPage.getTotal());

        return ResultVOUtil.success(sellerOrderListVO);
    }

    @PutMapping("/cancel/{orderId}")
    public ResultVO cancel(@PathVariable String orderId) {
        return ResultVOUtil.success(this.orderMasterService.sellerCancel(orderId));
    }

    @PutMapping("/finish/{orderId}")
    public ResultVO finish(@PathVariable String orderId) {
        return ResultVOUtil.success(this.orderMasterService.finish(orderId));
    }


}
