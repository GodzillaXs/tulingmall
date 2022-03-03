package com.tulingxueyuan.mall.modules.pms.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductAttribute;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
public interface ProductAttributeService extends IService<ProductAttribute> {

    Page getPageF(Long cid, Integer type, Integer pageNum, Integer pageSize);

    boolean create(ProductAttribute productAttribute);

    boolean deleteById(List<Long> ids);

    List<RelationAttrInfoDTO> getRelationAttrInfo(Long productCategoryId);
}
