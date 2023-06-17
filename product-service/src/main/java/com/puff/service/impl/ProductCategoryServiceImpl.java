package com.puff.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puff.entity.ProductCategory;
import com.puff.entity.ProductInfo;
import com.puff.mapper.ProductCategoryMapper;
import com.puff.mapper.ProductInfoMapper;
import com.puff.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.puff.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 类目表 服务实现类
 * </p>
 *
 * @author puff
 * @since 2023-06-02
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<BuyerProductCategoryVO> buyerProductCategoryVOList() {
        List<ProductCategory> productCategoryList = this.productCategoryMapper.selectList(null);
        ArrayList<BuyerProductCategoryVO> result = new ArrayList<BuyerProductCategoryVO>();
        for (ProductCategory productCategory : productCategoryList) {
            BuyerProductCategoryVO buyerProductCategoryVO = new BuyerProductCategoryVO();
            buyerProductCategoryVO.setName(productCategory.getCategoryName());
            buyerProductCategoryVO.setType(productCategory.getCategoryType());
//           给goods赋值
            QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("category_type",productCategory.getCategoryType());
            List<ProductInfo> productInfoList = this.productInfoMapper.selectList(queryWrapper);
            List<BuyerProductInfoVo> buyerProductCategoryVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                BuyerProductInfoVo buyerProductInfoVo = new BuyerProductInfoVo();
                BeanUtils.copyProperties(productInfo,buyerProductInfoVo);
                buyerProductCategoryVOList.add(buyerProductInfoVo);
            }
            buyerProductCategoryVO.setGoods(buyerProductCategoryVOList);
            result.add(buyerProductCategoryVO);
        }
        return result;
    }

    @Override
    public List<SellerProductCategoryVO> findAllProductCateGory() {
        List<ProductCategory> productCategoryList = this.productCategoryMapper.selectList(null);
        ArrayList<SellerProductCategoryVO> sellerProductCategoryVOS = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            SellerProductCategoryVO sellerProductCategoryVO = new SellerProductCategoryVO();
            sellerProductCategoryVO.setName(productCategory.getCategoryName());
            sellerProductCategoryVO.setType(productCategory.getCategoryType());
            sellerProductCategoryVOS.add(sellerProductCategoryVO);
        }
        return sellerProductCategoryVOS;
    }




}
