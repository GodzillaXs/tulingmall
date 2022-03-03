package com.tulingxueyuan.mall.modules.pms.service;


import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.ProductDTO;
import com.tulingxueyuan.mall.dto.ProductThreeDTO;
import com.tulingxueyuan.mall.dto.ProductTwoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.Product;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
public interface ProductService extends IService<Product> {

    Page list(ProductDTO productDTO);

    boolean updateStatus(Integer newStatus, List<Long> ids, SFunction<Product,?> getNewStatus);

    boolean create(ProductTwoDTO productTwoDTO);

    ProductThreeDTO updateInfoById(Long id);

    boolean update(ProductTwoDTO productTwoDTO);
}
