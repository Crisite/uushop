package com.puff;

import com.puff.service.ProductCategoryService;
import com.puff.vo.BuyerProductCategoryVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyuushopApplicationTests {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    void test() {
        List<BuyerProductCategoryVO> list = productCategoryService.buyerProductCategoryVOList();
        System.out.println(list);
    }
}
