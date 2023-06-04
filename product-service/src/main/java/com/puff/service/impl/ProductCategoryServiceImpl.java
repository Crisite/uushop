package com.puff.service.impl;

import com.puff.entity.ProductCategory;
import com.puff.mapper.ProductCategoryMapper;
import com.puff.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.puff.vo.BuyerProductCategoryVO;
import com.puff.vo.ResultVO;
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

    @Override
    public List<BuyerProductCategoryVO> buyerProductCategoryVOList() {
        List<ProductCategory> productCategoryList = this.productCategoryMapper.selectList(null);
        ArrayList<BuyerProductCategoryVO> result = new ArrayList<BuyerProductCategoryVO>();
        for (ProductCategory productCategory : productCategoryList) {
            BuyerProductCategoryVO buyerProductCategoryVO = new BuyerProductCategoryVO();
            buyerProductCategoryVO.setName(productCategory.getCategoryName());
            buyerProductCategoryVO.setType(productCategory.getCategoryType());
            result.add(buyerProductCategoryVO);
        }
        return result;
    }
}
