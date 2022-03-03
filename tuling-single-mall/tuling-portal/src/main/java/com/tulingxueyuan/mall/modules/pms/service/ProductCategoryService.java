package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.HomeMenusDTO;
import com.tulingxueyuan.mall.modules.pms.entity.ProductCategory;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-25
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    List<HomeMenusDTO> getMenus();
}
