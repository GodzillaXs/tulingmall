package com.tulingxueyuan.mall.modules.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mall.dto.ProductDTO;
import com.tulingxueyuan.mall.dto.ProductDetailDTO;
import com.tulingxueyuan.mall.modules.pms.entity.Product;

import java.util.List;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author fyl
 * @since 2021-11-25
 */
public interface ProductMapper extends BaseMapper<Product> {

    ProductDetailDTO getProductDetailById(Long id);

    List<ProductDTO> getSearchProduct(String keyword);
}
