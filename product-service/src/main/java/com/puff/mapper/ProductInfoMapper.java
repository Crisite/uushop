package com.puff.mapper;

import com.puff.entity.ProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author puff
 * @since 2023-06-02
 */
@Mapper
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
    Boolean updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
