package com.tulingxueyuan.mall.modules.pms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mall.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductAttribute;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 Mapper 接口
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
public interface ProductAttributeMapper extends BaseMapper<ProductAttribute> {


    List<RelationAttrInfoDTO> getRelationAttrInfo(Long productCategoryId);
}
