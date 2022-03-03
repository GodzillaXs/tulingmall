package com.tulingxueyuan.mall.modules.pms.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.ProductAttributeCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductAttributeCategory;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
public interface ProductAttributeCategoryService extends IService<ProductAttributeCategory> {

    Page getPageF(Integer pageNum, Integer pageSize);

    boolean add(ProductAttributeCategory productAttributeCategory);

    List<ProductAttributeCategoryDTO> listWithAttr();
}
