package com.puff.service;

import com.puff.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.puff.vo.BuyerProductCategoryVO;
import com.puff.vo.ResultVO;

import java.util.List;

/**
 * <p>
 * 类目表 服务类
 * </p>
 *
 * @author puff
 * @since 2023-06-02
 */

public interface ProductCategoryService extends IService<ProductCategory> {

    public List<BuyerProductCategoryVO> buyerProductCategoryVOList();
}
