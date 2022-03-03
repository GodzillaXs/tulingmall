package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.ProductDTO;
import com.tulingxueyuan.mall.dto.ProductDetailDTO;
import com.tulingxueyuan.mall.modules.pms.entity.Product;
import com.tulingxueyuan.mall.modules.pms.mapper.ProductMapper;
import com.tulingxueyuan.mall.modules.pms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-25
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public ProductDetailDTO getProductDetail(Long id) {
        return productMapper.getProductDetailById(id);
    }

    @Override
    public List<ProductDTO> simpleSearch(String keyword) {
        keyword="%"+keyword+"%";
        return productMapper.getSearchProduct(keyword);
    }
}
