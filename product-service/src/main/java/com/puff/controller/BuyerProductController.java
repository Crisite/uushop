package com.puff.controller;

import com.puff.service.ProductCategoryService;
import com.puff.vo.BuyerProductCategoryVO;
import com.puff.vo.ResultVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public List<BuyerProductCategoryVO> list() {
        return this.productCategoryService.buyerProductCategoryVOList();
    }
}
