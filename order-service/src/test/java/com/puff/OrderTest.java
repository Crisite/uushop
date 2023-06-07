package com.puff;

import com.puff.service.OrderMasterService;
import com.puff.service.impl.OrderMasterServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderMasterService orderMasterService;

    @Test
    public void Test(){
        this.orderMasterService.create(null);
    }
}
