package com.puff.controller;

import com.puff.entity.ProductCategory;
import com.puff.entity.ProductInfo;
import com.puff.service.ProductCategoryService;
import com.puff.service.ProductInfoService;
import com.puff.utils.ResultVOUtil;
import com.puff.vo.ResultVO;
import com.puff.vo.SellerProductCategoryVO;
import com.puff.vo.SellerProductInfoVO2;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoService productInfoService;

    @GetMapping("/findAllProductCateGory")
    public ResultVO findAllProductCateGory() {
        List<SellerProductCategoryVO> allProductCateGory = this.productCategoryService.findAllProductCateGory();

        HashMap<String, List<SellerProductCategoryVO>> map = new HashMap<>();
        map.put("content",allProductCateGory);

        return ResultVOUtil.success(map);
    }

    @PostMapping("/add")
    public ResultVO add(@RequestBody ProductInfo productInfo) {
        this.productInfoService.save(productInfo);

        return ResultVOUtil.success(null);
    }

    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable Integer page, @PathVariable Integer size) {
        SellerProductInfoVO2 sellerProductInfoVO2 = this.productInfoService.sellerProductInfoVO2(page, size);

        return ResultVOUtil.success(sellerProductInfoVO2);
    }
}
