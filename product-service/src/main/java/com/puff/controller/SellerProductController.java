package com.puff.controller;

import com.alibaba.excel.EasyExcel;
import com.puff.entity.ProductCategory;
import com.puff.entity.ProductInfo;
import com.puff.form.SellerProductInfoUpdateForm;
import com.puff.handler.CustomCellWriteHandler;
import com.puff.service.ProductCategoryService;
import com.puff.service.ProductInfoService;
import com.puff.utils.ResultVOUtil;
import com.puff.vo.ProductExcelVO;
import com.puff.vo.ResultVO;
import com.puff.vo.SellerProductCategoryVO;
import com.puff.vo.SellerProductInfoVO2;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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
    @GetMapping("/findAllProductCategory")
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

//    修改商品内容
    @PutMapping("/update")
    public ResultVO update(@RequestBody SellerProductInfoUpdateForm sellerProductInfoUpdateForm) {
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(sellerProductInfoUpdateForm, productInfo);
        productInfo.setCategoryType(sellerProductInfoUpdateForm.getCategory().getCategoryType());
        if (sellerProductInfoUpdateForm.getStatus()) {
            productInfo.setProductStatus(1);
        } else {
            productInfo.setProductStatus(0);
        }
        this.productInfoService.updateById(productInfo);
        return ResultVOUtil.success(null);
    }

//  导出excel
    @GetMapping("/export")
    public void exportData(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("商品信息", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //获取ProductExcelVO类型的List
            List<ProductExcelVO> productExcelVOList = this.productInfoService.excelVOList();
            EasyExcel.write(response.getOutputStream(), ProductExcelVO.class)
                    .registerWriteHandler(new CustomCellWriteHandler())
                    .sheet("商品信息")
                    .doWrite(productExcelVOList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    导入excel
    @PostMapping("/import")
    public ResultVO importData(@RequestParam("file") MultipartFile file){
        List<ProductInfo> productInfoList = null;
        try {
            productInfoList = this.productInfoService.excleToProductInfoList(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(productInfoList==null){
            return ResultVOUtil.fail("导入Excel失败！");
        }
        boolean result = this.productInfoService.saveBatch(productInfoList);
        if(result)return ResultVOUtil.success(null);
        return ResultVOUtil.fail("导入Excel失败！");
    }
}
