package com.puff.mapper;

import com.puff.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 类目表 Mapper 接口
 * </p>
 *
 * @author puff
 * @since 2023-06-02
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    String findNameByType(@Param("type") Integer type);
}
