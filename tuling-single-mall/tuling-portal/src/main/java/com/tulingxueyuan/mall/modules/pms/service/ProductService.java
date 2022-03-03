package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.ProductDTO;
import com.tulingxueyuan.mall.dto.ProductDetailDTO;
import com.tulingxueyuan.mall.modules.pms.entity.Product;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-25
 */
public interface ProductService extends IService<Product> {

    ProductDetailDTO getProductDetail(Long id);

    List<ProductDTO> simpleSearch(String keyword);
}
