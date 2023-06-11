package com.puff.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puff.entity.OrderMaster;
import com.puff.form.BuyerOrderForm;
import com.puff.service.OrderDetailService;
import com.puff.service.OrderMasterService;
import com.puff.utils.ResultVOUtil;
import com.puff.vo.OrderMasterVo;
import com.puff.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderMasterService orderMasterService;

    @PostMapping("/create")
    public ResultVO create(@RequestBody BuyerOrderForm buyerOrderForm) {
        String orderId = orderMasterService.create(buyerOrderForm);
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        return ResultVOUtil.success(map);
    }

    @GetMapping("/list/{buyerId}/{page}/{size}")
    public ResultVO list(@PathVariable("buyerId") Integer buyerId,
                         @PathVariable("page") Integer page,
                         @PathVariable("size") Integer size) {
        List<OrderMaster> orderMasterList = this.orderMasterService.list(buyerId, page, size);

        return ResultVOUtil.success(orderMasterList);
    }

    @GetMapping("detail/{buyerId}/{orderId}")
    public ResultVO detail(@PathVariable("buyerId") Integer buyerId,
                           @PathVariable("orderId") String orderid) {
        OrderMasterVo detail = this.orderMasterService.detail(orderid);

        return ResultVOUtil.success(detail);
    }
}
