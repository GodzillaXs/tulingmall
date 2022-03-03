package com.tulingxueyuan.mall.modules.pms.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.entity.SkuStock;
import com.tulingxueyuan.mall.modules.pms.mapper.SkuStockMapper;
import com.tulingxueyuan.mall.modules.pms.service.SkuStockService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku的库存 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@Service
public class SkuStockServiceImpl extends ServiceImpl<SkuStockMapper, SkuStock> implements SkuStockService {

    @Override
    public List<SkuStock> getSkuByProductId(Long pid, String keyword) {
        QueryWrapper<SkuStock> queryWrapper=new QueryWrapper<>();
        if (!StrUtil.isEmpty(keyword)){
            queryWrapper.lambda().like(SkuStock::getSkuCode,keyword);
        }
        queryWrapper.lambda().eq(SkuStock::getProductId,pid);
        return list(queryWrapper);
    }
}
