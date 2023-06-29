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

    @GetMapping("/detail/{buyerId}/{orderId}")
    public ResultVO detail(@PathVariable("buyerId") Integer buyerId,
                           @PathVariable("orderId") String orderid) {
        OrderMasterVo detail = this.orderMasterService.detail(buyerId, orderid);

        return ResultVOUtil.success(detail);
    }

    @PutMapping("/cancel/{buyerId}/{orderId}")
    public ResultVO cancel(@PathVariable("buyerId") Integer buyerId,
                           @PathVariable("orderId") String orderId) {

        this.orderMasterService.cancel(buyerId, orderId);

        return ResultVOUtil.success(null);
    }

    @PutMapping("/finish/{orderId}")
    public ResultVO finish(@PathVariable("orderId") String orderId) {
        this.orderMasterService.finish(orderId);
        return ResultVOUtil.success(null);
    }

    @PutMapping("/pay/{buyerId}/{orderId}")
    public ResultVO pay(@PathVariable("buyerId") Integer buyerId,
                        @PathVariable("orderId") String orderId){
        this.orderMasterService.pay(buyerId, orderId);
        return ResultVOUtil.success(null);
    }

}
