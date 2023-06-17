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
import org.apache.ibatis.annotations.Delete;
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

//    查询商品分类
    @GetMapping("/findAllProductCateGory")
    public ResultVO findAllProductCateGory() {
        List<SellerProductCategoryVO> allProductCateGory = this.productCategoryService.findAllProductCateGory();

        HashMap<String, List<SellerProductCategoryVO>> map = new HashMap<>();
        map.put("content",allProductCateGory);

        return ResultVOUtil.success(map);
    }

//    添加商品
    @PostMapping("/add")
    public ResultVO add(@RequestBody ProductInfo productInfo) {
        this.productInfoService.save(productInfo);

        return ResultVOUtil.success(null);
    }

//    商品列表
    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable Integer page, @PathVariable Integer size) {
        SellerProductInfoVO2 sellerProductInfoVO2 = this.productInfoService.sellerProductInfoVO2(page, size);

        return ResultVOUtil.success(sellerProductInfoVO2);
    }

//    模糊查询商品
    @GetMapping("/like/{keyword}/{page}/{size}")
    public ResultVO like(@PathVariable String keyword, @PathVariable Integer page, @PathVariable Integer size) {
        SellerProductInfoVO2 sellerProductInfoVO2 = this.productInfoService.sellerProductInfoLike(keyword, page, size);

        return ResultVOUtil.success(sellerProductInfoVO2);
    }

//    通过分类查询商品
    @GetMapping("/findByCategory/{categoryType}/{page}/{size}")
    public ResultVO like(@PathVariable Integer categoryType, @PathVariable Integer page, @PathVariable Integer size) {
        SellerProductInfoVO2 sellerProductInfoVO2 = this.productInfoService.findSellerProductInfoByCategory(categoryType, page, size);

        return ResultVOUtil.success(sellerProductInfoVO2);
    }

//    通过ID查询商品
    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable Integer id) {
        return ResultVOUtil.success(this.productInfoService.getById(id));
    }

//    通过Id删除商品
    @DeleteMapping("/delete/{id}")
    public ResultVO delete(@PathVariable Integer id) {
        this.productInfoService.removeById(id);
        return ResultVOUtil.success(null);
    }

//    修改商品状态
    @PutMapping("/updateStatus/{id}/{status}")
    public ResultVO updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        this.productInfoService.updateStatus(id,status);
        return ResultVOUtil.success(null);
    }
}
