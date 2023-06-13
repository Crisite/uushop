package com.puff.controller;

import com.puff.entity.ProductInfo;
import com.puff.service.ProductCategoryService;
import com.puff.service.ProductInfoService;
import com.puff.vo.BuyerProductCategoryVO;
import com.puff.vo.ResultVO;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoService productInfoService;

    @GetMapping("/list")
    public List<BuyerProductCategoryVO> list() {
        return this.productCategoryService.buyerProductCategoryVOList();
    }

    @GetMapping("/findPriceById/{id}")
    public BigDecimal findPriceById(@PathVariable("id")int id) {
        ProductInfo productInfo = this.productInfoService.getById(id);
        return productInfo.getProductPrice();
    }

    @GetMapping("/findById/{id}")
    public ProductInfo findById(@PathVariable("id")int id) {
        return this.productInfoService.getById(id);
    }

    @PutMapping("/subStockById/{id}/{quantity}")
    public Boolean subStockById(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity) {
        return this.productInfoService.subStockById(id, quantity);
    }

    @PutMapping("/addStockById/{id}/{quantity}")
    public Boolean addStockById(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity) {
        return this.productInfoService.addStockById(id, quantity);
    }

}
