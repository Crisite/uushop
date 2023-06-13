package com.puff.service.impl;

import com.puff.entity.ProductInfo;
import com.puff.mapper.ProductInfoMapper;
import com.puff.service.ProductInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
