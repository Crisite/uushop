package com.puff.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.puff.form.BuyerOrderForm;
import com.puff.service.OrderDetailService;
import com.puff.service.OrderMasterService;
import com.puff.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderMasterService orderMasterService;

    @PostMapping("create")
    public ResultVO create(@RequestBody BuyerOrderForm buyerOrderForm) {
        System.out.println(buyerOrderForm);
        buyerOrderForm.
        return null;
    }
}
