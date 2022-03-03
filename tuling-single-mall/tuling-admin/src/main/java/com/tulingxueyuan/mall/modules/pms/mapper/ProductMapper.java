package com.tulingxueyuan.mall.modules.pms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mall.dto.ProductThreeDTO;
import com.tulingxueyuan.mall.modules.pms.entity.Product;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
public interface ProductMapper extends BaseMapper<Product> {

    ProductThreeDTO updateInfoById(Long id);
}
