package com.tulingxueyuan.mall.modules.pms.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.ProductAttributeCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.mapper.ProductAttributeCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.service.ProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@Service
public class ProductAttributeCategoryServiceImpl extends ServiceImpl<ProductAttributeCategoryMapper, ProductAttributeCategory> implements ProductAttributeCategoryService {

    @Autowired
    ProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Override
    public Page getPageF(Integer pageNum, Integer pageSize) {
        Page page=new Page(pageNum,pageSize);
        return this.page(page);
    }

    @Override
    public boolean add(ProductAttributeCategory productAttributeCategory) {
        productAttributeCategory.setAttributeCount(0);
        productAttributeCategory.setParamCount(0);
        return this.save(productAttributeCategory);
    }

    @Override
    public List<ProductAttributeCategoryDTO> listWithAttr() {
        return productAttributeCategoryMapper.listWithAttr();
    }
}
