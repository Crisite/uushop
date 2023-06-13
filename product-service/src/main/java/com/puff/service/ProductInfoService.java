package com.puff.service;

import com.puff.entity.ProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
