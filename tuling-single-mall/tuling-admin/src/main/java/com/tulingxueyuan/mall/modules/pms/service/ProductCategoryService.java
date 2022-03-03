package com.tulingxueyuan.mall.modules.pms.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.ProductCategoryDTO;
import com.tulingxueyuan.mall.dto.ProductCategoryTwoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductCategory;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    Page getPageF(Long parentId, Integer pageNum, Integer pageSize);

    boolean updateNavStatus(List<Integer> ids, Integer navStatus);

    boolean updateShowStatus(List<Integer> ids, Integer showStatus);

    boolean create(ProductCategoryDTO productCategoryDTO);


    boolean updateByIdF(ProductCategoryDTO productCategoryDTO);

    List<ProductCategoryTwoDTO> listWithChildren();
}
