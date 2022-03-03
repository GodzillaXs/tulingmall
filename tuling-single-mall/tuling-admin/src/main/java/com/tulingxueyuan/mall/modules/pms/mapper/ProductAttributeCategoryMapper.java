package com.tulingxueyuan.mall.modules.pms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mall.dto.ProductAttributeCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductAttributeCategory;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 Mapper 接口
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
public interface ProductAttributeCategoryMapper extends BaseMapper<ProductAttributeCategory> {

    List<ProductAttributeCategoryDTO> listWithAttr();
}
