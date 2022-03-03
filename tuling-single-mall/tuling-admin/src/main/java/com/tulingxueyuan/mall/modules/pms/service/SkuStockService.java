package com.tulingxueyuan.mall.modules.pms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.pms.entity.SkuStock;

import java.util.List;

/**
 * <p>
 * sku的库存 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
public interface SkuStockService extends IService<SkuStock> {

    List<SkuStock> getSkuByProductId(Long pid, String keyword);
}
