package com.tulingxueyuan.mall.modules.pms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mall.dto.ProductCategoryTwoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductCategory;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    List<ProductCategoryTwoDTO> listWithChildren();
}
