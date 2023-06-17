package com.puff.service;

import com.puff.entity.ProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.puff.vo.SellerProductInfoVO;
import com.puff.vo.SellerProductInfoVO2;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author puff
 * @since 2023-06-02
 */
public interface ProductInfoService extends IService<ProductInfo> {
    Boolean addStockById(Integer id, Integer quantity);
    Boolean subStockById(Integer id, Integer quantity);
    SellerProductInfoVO2 sellerProductInfoVO2(Integer page, Integer size);
}
