package com.puff.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puff.entity.ProductCategory;
import com.puff.entity.ProductInfo;
import com.puff.mapper.ProductCategoryMapper;
import com.puff.mapper.ProductInfoMapper;
import com.puff.service.ProductCategoryService;
import com.puff.service.ProductInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.puff.vo.ProductExcelVO;
import com.puff.vo.SellerProductInfoVO;
import com.puff.vo.SellerProductInfoVO2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author puff
 * @since 2023-06-02
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public Boolean addStockById(Integer id, Integer quantity) {
        ProductInfo productInfo = this.productInfoMapper.selectById(id);
        Integer productStock = productInfo.getProductStock();
        Integer result = productStock + quantity;
        productInfo.setProductStock(result);
        productInfoMapper.updateById(productInfo);

        return true;
    }

    @Override
    public Boolean subStockById(Integer id, Integer quantity) {
        ProductInfo productInfo = this.productInfoMapper.selectById(id);
        Integer productStock = productInfo.getProductStock();
        Integer result = productStock - quantity;
        if(result < 0) throw new RuntimeException("库存不足");
        productInfo.setProductStock(result);
        this.productInfoMapper.updateById(productInfo);
        return true;
    }

    @Override
    public SellerProductInfoVO2 sellerProductInfoVO2(Integer page, Integer size) {
        Page<ProductInfo> productInfoPage = new Page<>(page, size);
        Page<ProductInfo> resultPage = this.productInfoMapper.selectPage(productInfoPage, null);
        return getSellerProductInfoVO2(resultPage);
    }

    @Override
    public SellerProductInfoVO2 sellerProductInfoLike(String keyword, Integer page, Integer size) {
        Page<ProductInfo> productInfoPage = new Page<>(page, size);
        QueryWrapper<ProductInfo> productInfoQueryWrapper = new QueryWrapper<>();
        productInfoQueryWrapper.like("product_name",keyword);
        Page<ProductInfo> resultPage = this.productInfoMapper.selectPage(productInfoPage, productInfoQueryWrapper);
        return getSellerProductInfoVO2(resultPage);
    }

    @Override
    public SellerProductInfoVO2 findSellerProductInfoByCategory(Integer categoryType, Integer page, Integer size) {
        Page<ProductInfo> productInfoPage = new Page<>(page, size);
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_type",categoryType);
        Page<ProductInfo> resultPage = this.productInfoMapper.selectPage(productInfoPage, queryWrapper);
        return getSellerProductInfoVO2(resultPage);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        this.productInfoMapper.updateStatus(id,status);
    }

    @Override
    public List<ProductExcelVO> excelVOList() {
        List<ProductInfo> productInfoList = this.productInfoMapper.selectList(null);
        ArrayList<ProductExcelVO> productExcelVOS = new ArrayList<>();
        for (ProductInfo productInfo : productInfoList) {
            ProductExcelVO productExcelVO = new ProductExcelVO();
            BeanUtils.copyProperties(productInfo,productExcelVO);
            productExcelVO.setCategoryName(this.productCategoryMapper.findNameByType(productInfo.getCategoryType()));
            if (productInfo.getProductStatus() == 1) {
                productExcelVO.setProductStatus("上架");
            } else {
                productExcelVO.setProductStatus("下架");
            }
            productExcelVOS.add(productExcelVO);
        }
        return productExcelVOS;
    }
    @Override
    public List<ProductInfo> excleToProductInfoList(InputStream inputStream) {
        try {
            List<ProductInfo> list = new ArrayList<>();
            EasyExcel.read(inputStream)
                    .head(ProductExcelVO.class)
                    .sheet()
                    .registerReadListener(new AnalysisEventListener<ProductExcelVO>() {

                        @Override
                        public void invoke(ProductExcelVO excelData, AnalysisContext analysisContext) {
                            ProductInfo productInfo = new ProductInfo();
                            BeanUtils.copyProperties(excelData, productInfo);
                            if(excelData.getProductStatus().equals("正常")){
                                productInfo.setProductStatus(1);
                            }else{
                                productInfo.setProductStatus(0);
                            }
                            list.add(productInfo);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                            log.debug("=========================文件解析完成=========================");
                        }
                    }).doRead();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //   很据查询结果Page返回产品信息列表
    private SellerProductInfoVO2 getSellerProductInfoVO2(Page<ProductInfo> resultPage) {
        ArrayList<SellerProductInfoVO> sellerProductInfoVOS = new ArrayList<>();
        for (ProductInfo productInfo : resultPage.getRecords()) {
            SellerProductInfoVO sellerProductInfoVO = new SellerProductInfoVO();
            BeanUtils.copyProperties(productInfo, sellerProductInfoVO);
            sellerProductInfoVO.setStatus(productInfo.getProductStatus() == 1);

            Integer categoryType = productInfo.getCategoryType();
            QueryWrapper<ProductCategory> productCategoryQueryWrapper = new QueryWrapper<>();
            productCategoryQueryWrapper.eq("category_type",categoryType);
            ProductCategory productCategory = this.productCategoryMapper.selectOne(productCategoryQueryWrapper);
            sellerProductInfoVO.setCategoryType(productCategory.getCategoryName());

            sellerProductInfoVOS.add(sellerProductInfoVO);
        }
        SellerProductInfoVO2 sellerProductInfoVO2 = new SellerProductInfoVO2();
        sellerProductInfoVO2.setContent(sellerProductInfoVOS);
        sellerProductInfoVO2.setSize(resultPage.getSize());
        sellerProductInfoVO2.setTotal(resultPage.getTotal());

        return sellerProductInfoVO2;
    }
}
